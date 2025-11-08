package com.sapienza.reverie

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.sapienza.reverie.presentation.ui.navigation.AppNavigation
import com.sapienza.reverie.presentation.ui.screen.LoginScreen
import com.sapienza.reverie.ui.theme.ReverieTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ReverieTheme { // By default, dynamicColor is now false
                AppNavigation()
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