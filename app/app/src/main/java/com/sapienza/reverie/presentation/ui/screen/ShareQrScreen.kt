package com.sapienza.reverie.presentation.ui.screen

import android.graphics.Bitmap
import android.graphics.Color
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.zxing.BarcodeFormat
import com.google.zxing.qrcode.QRCodeWriter
import com.sapienza.reverie.presentation.theme.ReverieFontFamily
import com.sapienza.reverie.presentation.ui.components.HomeNavBar
import com.sapienza.reverie.presentation.ui.components.NavBar
import com.sapienza.reverie.presentation.ui.components.TopBar

@Composable
fun ShareQrScreen(
    charmId: Long,
    onHomeClick: () -> Unit = {},
    onCollectionClick: () -> Unit = {},
) {
    // Generate the QR code from the charmId
    val qrCodeBitmap = remember(charmId) {
        generateQrCode(charmId.toString())
    }

    Scaffold(
        bottomBar = { HomeNavBar(onHomeClick, onCollectionClick) }
    )
     { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Scan to Collect this Charm!",
                fontSize = 22.sp,
                fontFamily = ReverieFontFamily("Bold"),
            )

            Spacer(modifier = Modifier.height(32.dp))

            if (qrCodeBitmap != null) {
                Image(
                    bitmap = qrCodeBitmap.asImageBitmap(),
                    contentDescription = "Charm QR Code",
                    modifier = Modifier
                        .size(300.dp)
                        .padding(16.dp)
                        .background(MaterialTheme.colorScheme.primaryContainer),
                    contentScale = ContentScale.Fit
                )
            } else {
                Text("Could not generate QR code.")
            }

            Spacer(modifier = Modifier.height(16.dp))

        }
    }
}

private fun generateQrCode(content: String): Bitmap? {
    val writer = QRCodeWriter()
    return try {
        val bitMatrix = writer.encode(content, BarcodeFormat.QR_CODE, 512, 512)
        val width = bitMatrix.width
        val height = bitMatrix.height
        val bmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)
        for (x in 0 until width) {
            for (y in 0 until height) {
                bmp.setPixel(x, y, if (bitMatrix[x, y]) Color.BLACK else Color.WHITE)
            }
        }
        bmp
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}
