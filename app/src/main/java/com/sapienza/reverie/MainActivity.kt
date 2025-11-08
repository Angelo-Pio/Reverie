package com.sapienza.reverie

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.sapienza.reverie.presentation.ui.components.CharmCarousel
import com.sapienza.reverie.presentation.ui.components.GoogleSearchBar
import com.sapienza.reverie.presentation.ui.screen.CollectionScreen
import com.sapienza.reverie.presentation.ui.screen.DashboardScreen
import com.sapienza.reverie.presentation.ui.screen.LoginScreen
import com.sapienza.reverie.ui.theme.ReverieTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ReverieTheme { // By default, dynamicColor is now false
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    // The background color will now come from your custom theme
                    color = MaterialTheme.colorScheme.background
                ) {
                    // Your app's navigation or main screen goes here
                    CollectionScreen()
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ReverieTheme {
        LoginScreen {  }
    }
}