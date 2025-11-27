package com.sapienza.reverie.presentation.ui.screen

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.os.Build
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.OptIn
import androidx.camera.core.CameraSelector
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.android.gms.location.LocationServices
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage
import com.sapienza.reverie.domain.model.CharmModel
import com.sapienza.reverie.presentation.ui.components.HomeNavBar
import com.sapienza.reverie.presentation.viewmodel.CharmViewModel
import com.sapienza.reverie.presentation.viewmodel.SessionViewModel
import java.util.Locale

@Composable
fun ScanQrScreen(
    onHomeClick: () -> Unit,
    onCollectionClick: () -> Unit,
    onCharmCollected: (CharmModel) -> Unit // Callback to return the scanned charm
) {
    val context = LocalContext.current
    var hasCameraPermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context, Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        )
    }
    var hasLocationPermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context, Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        )
    }

    // This launcher will request the camera and location permissions
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
        onResult = { permissions ->
            hasCameraPermission = permissions[Manifest.permission.CAMERA] ?: hasCameraPermission
            hasLocationPermission = permissions[Manifest.permission.ACCESS_FINE_LOCATION] ?: hasLocationPermission
        })

    // Request permissions on first composition if we don't have them
    LaunchedEffect(key1 = true) {
        val permissionsToRequest = mutableListOf<String>()
        if (!hasCameraPermission) {
            permissionsToRequest.add(Manifest.permission.CAMERA)
        }
        if (!hasLocationPermission) {
            permissionsToRequest.add(Manifest.permission.ACCESS_FINE_LOCATION)
        }
        if (permissionsToRequest.isNotEmpty()) {
            permissionLauncher.launch(permissionsToRequest.toTypedArray())
        }
    }

    Scaffold(
        bottomBar = { HomeNavBar(onHomeClick, onCollectionClick) }) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (hasCameraPermission && hasLocationPermission) {
                // We have permissions, show the camera preview
                Box(modifier = Modifier.fillMaxSize()) {
                    CameraView(
                        onCharmCollected = onCharmCollected
                    )
                    Text(
                        text = "Point the camera at a QR code",
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .padding(bottom = 32.dp)
                    )
                }
            } else {
                // We don't have permissions, show a message
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        if (!hasCameraPermission) {
                            Text(
                                "Camera permission is required to scan QR codes.",
                                textAlign = TextAlign.Center,
                                modifier = Modifier.padding(8.dp)
                            )
                        }
                        if (!hasLocationPermission) {
                            Text(
                                "Location permission is required to save where the charm was collected.",
                                textAlign = TextAlign.Center,
                                modifier = Modifier.padding(8.dp)
                            )
                        }
                        Button(onClick = {
                            val permissionsToRequest = mutableListOf<String>()
                            if (!hasCameraPermission) {
                                permissionsToRequest.add(Manifest.permission.CAMERA)
                            }
                            if (!hasLocationPermission) {
                                permissionsToRequest.add(Manifest.permission.ACCESS_FINE_LOCATION)
                            }
                            if (permissionsToRequest.isNotEmpty()) {
                                permissionLauncher.launch(permissionsToRequest.toTypedArray())
                            }
                        }) {
                            Text("Grant Permissions")
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalGetImage::class)
@SuppressLint("MissingPermission")
@Composable
private fun CameraView(onCharmCollected: (CharmModel) -> Unit) {

    val sessionViewModel: SessionViewModel = viewModel()
    val charmViewModel: CharmViewModel = viewModel()
    val user by sessionViewModel.user.collectAsState()
    val newlyCollectedCharm by charmViewModel.newlyCollectedCharm.collectAsState()

    val context = LocalContext.current
    val activity = context as Activity
    val lifecycleOwner = LocalLifecycleOwner.current

    val fusedLocationClient =
        remember { LocationServices.getFusedLocationProviderClient(context) }

    val geocoder = remember { Geocoder(context, Locale.getDefault()) }

    // States
    var isWaitingForLocation by remember { mutableStateOf(false) }
    var hasScanned by remember { mutableStateOf(false) }

    // Return charm result
    LaunchedEffect(newlyCollectedCharm) {
        newlyCollectedCharm?.let { charm ->
            onCharmCollected(charm)
            charmViewModel.clearNewlyCollectedCharm()
        }
    }

    // --------------------------------------------
    //  GPS ENABLE DIALOG (Google Maps style)
    // --------------------------------------------

    val gpsEnableLauncher =
        rememberLauncherForActivityResult(
            ActivityResultContracts.StartIntentSenderForResult()
        ) { result ->
            // If user enabled GPS, location updates will work automatically afterwards
        }

    fun isGpsEnabled(): Boolean {
        val lm = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return lm.isProviderEnabled(LocationManager.GPS_PROVIDER)
    }

    fun requestGpsEnablePopup() {
        val locationRequest = com.google.android.gms.location.LocationRequest.Builder(
            com.google.android.gms.location.Priority.PRIORITY_HIGH_ACCURACY,
            1000L
        ).build()

        val settingsRequest = com.google.android.gms.location.LocationSettingsRequest.Builder()
            .addLocationRequest(locationRequest)
            .setAlwaysShow(true)
            .build()

        val settingsClient = LocationServices.getSettingsClient(context)

        settingsClient.checkLocationSettings(settingsRequest)
            .addOnFailureListener { ex ->
                if (ex is com.google.android.gms.common.api.ResolvableApiException) {
                    try {
                        val intentSenderRequest =
                            IntentSenderRequest.Builder(ex.resolution).build()
                        gpsEnableLauncher.launch(intentSenderRequest)

                    } catch (sendEx: Exception) {
                        Log.e("ScanQr", "Failed to show GPS enable dialog", sendEx)
                    }
                }
            }
    }

    // --------------------------------------------
    //  REQUEST FRESH LOCATION FIX
    // --------------------------------------------

    fun requestFreshLocation(onDone: (Location?) -> Unit) {

        // If GPS off → show system popup and wait
        if (!isGpsEnabled()) {
            isWaitingForLocation = true
            requestGpsEnablePopup()
            return
        }

        isWaitingForLocation = true

        val request = com.google.android.gms.location.LocationRequest.Builder(
            com.google.android.gms.location.Priority.PRIORITY_HIGH_ACCURACY,
            1000L
        )
            .setWaitForAccurateLocation(true)
            .setMinUpdateIntervalMillis(500L)
            .setMaxUpdateDelayMillis(2000L)
            .build()

        val callback = object : com.google.android.gms.location.LocationCallback() {
            override fun onLocationResult(result: com.google.android.gms.location.LocationResult) {
                fusedLocationClient.removeLocationUpdates(this)

                val loc = result.lastLocation
                isWaitingForLocation = false
                onDone(loc)
            }
        }

        fusedLocationClient.requestLocationUpdates(
            request,
            callback,
            context.mainLooper
        )
    }

    // --------------------------------------------
    //  CAMERA + QR SCANNER
    // --------------------------------------------

    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = { ctx ->
            val previewView = PreviewView(ctx)
            val cameraProviderFuture = ProcessCameraProvider.getInstance(ctx)
            val executor = ContextCompat.getMainExecutor(ctx)

            cameraProviderFuture.addListener({
                val cameraProvider = cameraProviderFuture.get()

                val preview = Preview.Builder().build().also {
                    it.setSurfaceProvider(previewView.surfaceProvider)
                }

                val imageAnalysis = ImageAnalysis.Builder()
                    .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                    .build()

                val scanner = BarcodeScanning.getClient(
                    BarcodeScannerOptions.Builder()
                        .setBarcodeFormats(Barcode.FORMAT_QR_CODE)
                        .build()
                )

                imageAnalysis.setAnalyzer(executor) { imageProxy ->
                    if (hasScanned || isWaitingForLocation) {
                        imageProxy.close()
                        return@setAnalyzer
                    }

                    val mediaImage = imageProxy.image
                    if (mediaImage != null) {
                        val inputImage = InputImage.fromMediaImage(
                            mediaImage,
                            imageProxy.imageInfo.rotationDegrees
                        )

                        scanner.process(inputImage)
                            .addOnSuccessListener { barcodes ->
                                val raw = barcodes.firstOrNull()?.rawValue ?: ""
                                if (raw.isNotEmpty() && !hasScanned) {

                                    hasScanned = true
                                    val charmId = raw.toLongOrNull()
                                    val userId = user?.id

                                    if (charmId != null && userId != null) {

                                        // START LIVE GPS FIX
                                        requestFreshLocation { location ->

                                            if (location == null) {
                                                charmViewModel.collectCharm(
                                                    userId, charmId, "Location unavailable"
                                                )
                                                return@requestFreshLocation
                                            }

                                            // GEOCODER
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                                                geocoder.getFromLocation(
                                                    location.latitude,
                                                    location.longitude,
                                                    1
                                                ) { addresses ->

                                                    val collectedIn =
                                                        addresses.firstOrNull()?.getAddressLine(0)
                                                            ?: "Unknown location"

                                                    charmViewModel.collectCharm(
                                                        userId, charmId, collectedIn
                                                    )
                                                }

                                            } else {
                                                @Suppress("DEPRECATION")
                                                val addresses = geocoder.getFromLocation(
                                                    location.latitude,
                                                    location.longitude,
                                                    1
                                                )
                                                val collectedIn =
                                                    addresses?.firstOrNull()?.getAddressLine(0)
                                                        ?: "Unknown location"

                                                charmViewModel.collectCharm(
                                                    userId, charmId, collectedIn
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                            .addOnCompleteListener { imageProxy.close() }
                    } else {
                        imageProxy.close()
                    }
                }

                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(
                    lifecycleOwner,
                    CameraSelector.DEFAULT_BACK_CAMERA,
                    preview,
                    imageAnalysis
                )

            }, executor)

            previewView
        }
    )

    // --------------------------------------------
    //  WAITING POPUP
    // --------------------------------------------

    if (isWaitingForLocation) {
        AlertDialog(
            onDismissRequest = {},
            confirmButton = {},
            title = { Text("Trying to obtain current location") },
            text = {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(10.dp)
                ) {
                    Text("Make sure to enable your GPS")
                    CircularProgressIndicator(modifier = Modifier.padding(top = 16.dp))
                }
            }
        )
    }
}
