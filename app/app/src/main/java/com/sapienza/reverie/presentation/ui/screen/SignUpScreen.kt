package com.sapienza.reverie.presentation.ui.screen

import android.content.Context
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.sapienza.reverie.R
import com.sapienza.reverie.domain.model.UserModel
import com.sapienza.reverie.presentation.theme.ReverieFontFamily
import com.sapienza.reverie.presentation.viewmodel.SessionViewModel
import com.sapienza.reverie.ui.theme.ReverieTheme
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream

@Composable
fun SignUpScreen(
    onSignUpSuccess: () -> Unit = {},
    onLoginClick: () -> Unit = {}
) {
    // States for username, email, password, and password visibility
    var password by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var isPasswordVisible by remember { mutableStateOf(false) }

    // State for the selected profile picture URI
    var imageUri by remember { mutableStateOf<Uri?>(null) }

    val context = LocalContext.current
    val sessionViewModel: SessionViewModel = viewModel()
    val user by sessionViewModel.user.collectAsState()
    val signUpError by sessionViewModel.signUpError.collectAsState()

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    // --- Image picker launcher ---
    // This allows the user to select an image from their device's gallery.
    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        imageUri = uri
    }

    // --- Side Effects for UI events ---
    LaunchedEffect(signUpError) {
        signUpError?.let { error ->
            scope.launch {
                snackbarHostState.showSnackbar(error)

            }
        }
    }

    LaunchedEffect(user) {
        if (user != null) {
            onSignUpSuccess() // Navigate on successful signup
        }
    }

    val gradientColors = listOf(
        Color(0xFFDEF1ED),  // Light Blue
        Color.White,
        Color(0xFFEBE6FF)   // Light Purple
    )

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(brush = Brush.linearGradient(colors = gradientColors)),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Create Account",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.ExtraBold,
                    fontFamily = ReverieFontFamily("Bold"),
                    fontSize = 30.sp
                )

                Spacer(modifier = Modifier.height(24.dp))

                // --- Profile Picture Picker UI ---
                Box(
                    modifier = Modifier
                        .size(120.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.surfaceVariant)
                        .clickable { imagePickerLauncher.launch("image/*") }, // Open gallery on click
                    contentAlignment = Alignment.Center
                ) {
                    if (imageUri != null) {
                        AsyncImage(
                            model = imageUri,
                            contentDescription = "Profile Picture",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    } else {
                        Image(
                            painter = painterResource(id = R.drawable.reverie_logo),
                            contentDescription = "Upload Profile Picture",
                            modifier = Modifier.size(60.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // --- Input Fields ---
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = username, onValueChange = { username = it },
                    label = { Text("Username") }, singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                    shape = RoundedCornerShape(12.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = email, onValueChange = { email = it },
                    label = { Text("Email") }, singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    shape = RoundedCornerShape(12.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = password, onValueChange = { password = it },
                    label = { Text("Password") }, singleLine = true,
                    visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    trailingIcon = {
                        val image = if (isPasswordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                        IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                            Icon(imageVector = image, contentDescription = if (isPasswordVisible) "Hide password" else "Show password")
                        }
                    },
                    shape = RoundedCornerShape(12.dp)
                )

                Spacer(modifier = Modifier.height(24.dp))

                // --- Sign Up Button with validation ---
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        val file = imageUri?.let { uri -> uriToFile(context, uri) }
                        if (username.isBlank() || email.isBlank() || password.isBlank() || file == null) {
                            scope.launch {
                                snackbarHostState.showSnackbar("Please fill all fields and select a picture.")
                            }
                        } else {
                            val userModel = UserModel(username = username, email = email, password = password, id = 0, profilePicture = "")
                            sessionViewModel.signUp(userModel, file)
                        }
                    }
                ) {
                    Text("SIGN UP")
                }

                Spacer(modifier = Modifier.height(16.dp))
                TextButton(onClick = onLoginClick) {
                    Text("Already have an account? Log In")
                }
            }
        }
    }
}

// Helper function to convert a content URI to a temporary File
private fun uriToFile(context: Context, uri: Uri): File? {
    return try {
        val inputStream = context.contentResolver.openInputStream(uri)
        val file = File(context.cacheDir, "temp_profile_pic.jpg")
        val outputStream = FileOutputStream(file)
        inputStream?.copyTo(outputStream)
        inputStream?.close()
        outputStream.close()
        file
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

@Preview(showBackground = true)
@Composable
fun SignUpScreenPreview() {
    ReverieTheme {
        SignUpScreen()
    }
}
