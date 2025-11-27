package com.sapienza.reverie

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.sapienza.reverie.presentation.ui.navigation.AppNavigation
import com.sapienza.reverie.presentation.viewmodel.CharmViewModel
import com.sapienza.reverie.presentation.ui.screen.LoginScreen
import com.sapienza.reverie.ui.theme.ReverieTheme


/*
    TODO:
        * JWT for session handling --> maybe not necessary
        * GPS for localization (where you collected ? )


 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ReverieTheme {
                AppNavigation()
            }

        }

    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ReverieTheme {
        LoginScreen { }
    }
}