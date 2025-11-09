package com.sapienza.reverie.presentation.ui.navigation

import com.sapienza.reverie.domain.model.CharmModel
import kotlinx.serialization.Serializable

@Serializable // The entire sealed interface/class must be serializable
sealed interface Screen {
    @Serializable
    data object Login : Screen

    @Serializable
    data object Home : Screen

    @Serializable
    data object Collection : Screen

    @Serializable
    data object ScanQR : Screen

    @Serializable
    data object CharmEdit : Screen

    @Serializable
    data object SearchImage : Screen

    // IMPORTANT: Charm is now a data class holding the specific charm data
    @Serializable
    data class Charm(val charmModel: CharmModel) : Screen
}