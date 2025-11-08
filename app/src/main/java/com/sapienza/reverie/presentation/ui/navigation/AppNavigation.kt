package com.sapienza.reverie.presentation.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import com.sapienza.reverie.presentation.ui.screen.CharmEditScreen
import com.sapienza.reverie.presentation.ui.screen.CharmScreen
import com.sapienza.reverie.presentation.ui.screen.CollectionScreen


import com.sapienza.reverie.presentation.ui.screen.DashboardScreen
import com.sapienza.reverie.presentation.ui.screen.LoginScreen
import com.sapienza.reverie.presentation.ui.screen.ScanQrScreen
import java.util.Map.entry


@Composable
fun AppNavigation(){

    val backstack = remember { mutableStateListOf<Screen>(Screen.Login) }

    NavDisplay(
        backStack = backstack,

        entryProvider = entryProvider {
            entry<Screen.Login>{
                LoginScreen(onLoginClick = {backstack.add(Screen.Dashboard)})
            }
            entry<Screen.Dashboard>{
                DashboardScreen()
            }
            entry<Screen.CharmEdit>{
                CharmEditScreen()
            }
            entry<Screen.Charm>{
                CharmScreen()
            }
            entry<Screen.Collection>{
                CollectionScreen()
            }
            entry<Screen.ScanQR>{
                ScanQrScreen()
            }
        }
    )

}