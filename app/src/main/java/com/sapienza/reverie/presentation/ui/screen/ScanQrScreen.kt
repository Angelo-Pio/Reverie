package com.sapienza.reverie.presentation.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import com.sapienza.reverie.presentation.theme.ReverieFontFamily
import com.sapienza.reverie.presentation.ui.components.NavBar
import com.sapienza.reverie.presentation.ui.components.TopBar
import com.sapienza.reverie.ui.theme.ReverieTheme


@Composable
fun ScanQrScreen(modifier: Modifier = Modifier){
    val hasCameraPermission = remember { mutableStateOf(false) }



    Scaffold(bottomBar = { NavBar() }, topBar = { TopBar(title = "Scan the QR Code", icon = Icons.Filled.QrCodeScanner) }){
        innerPadding->
        Column(
            modifier = modifier.padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.height(32.dp))
            Text(text = "Scan a reverie code to get a new charm!", fontSize = 24.sp, fontFamily = ReverieFontFamily("Bold"), modifier = modifier.padding(horizontal = 24.dp), textAlign = TextAlign.Center)
            Spacer(modifier = Modifier.height(32.dp))

            Surface(
                modifier = Modifier.fillMaxSize().aspectRatio(1f/1f).padding(24.dp),
                shape = RoundedCornerShape(16.dp),
                color = MaterialTheme.colorScheme.primary
            ) {
                Icon(
                    imageVector = Icons.Filled.QrCodeScanner,
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize().padding(8.dp)
                )
            }

        }
    }
}

@Preview
@Composable
fun ScanQrCode(){
    ReverieTheme {
        ScanQrScreen()
    }
}