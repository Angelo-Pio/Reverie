package com.sapienza.reverie.presentation.ui.navigation

sealed class Screen{
    data object Login : Screen()
    data object Dashboard : Screen()
    data object Collection : Screen()
    data object ScanQR : Screen()
    data object Charm : Screen()
    data object CharmEdit : Screen()

}