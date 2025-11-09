package com.sapienza.reverie.presentation.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember

import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import com.sapienza.reverie.presentation.ui.screen.CharmEditScreen
import com.sapienza.reverie.presentation.ui.screen.CharmScreen
import com.sapienza.reverie.presentation.ui.screen.CollectionScreen


import com.sapienza.reverie.presentation.ui.screen.DashboardScreen
import com.sapienza.reverie.presentation.ui.screen.LoginScreen
import com.sapienza.reverie.presentation.ui.screen.ScanQrScreen


@Composable
fun AppNavigation(){

    val backstack = remember { mutableStateListOf<Screen>(Screen.Login) }

    NavDisplay(
        backStack = backstack,

        entryProvider = entryProvider {
            entry<Screen.Login>{
                LoginScreen(onLoginClick = {backstack.add(Screen.Home)})
            }
            entry<Screen.Home>{
                DashboardScreen(
                    onScanClick = {backstack.add(Screen.ScanQR)},
                    onEditClick = {backstack.add(Screen.CharmEdit) },
                    onHomeClick = {backstack.add(Screen.Home)},
                    onCollectionClick = {backstack.add(Screen.Collection)}

                )
            }
            entry<Screen.CharmEdit>{
                CharmEditScreen(
                    onCancelClick = {backstack.add(Screen.Home)}
                )
            }
            entry<Screen.Collection>{
                CollectionScreen(
                    onCharmClick = { charmModel -> backstack.add(Screen.Charm(charmModel)) },
                    onEditClick = {backstack.add(Screen.CharmEdit) },
                    onHomeClick = {backstack.add(Screen.Home)},
                    onCollectionClick = {backstack.add(Screen.Collection)},
                )
            }
            entry<Screen.Charm>{ screen ->
                CharmScreen(
                    charmModel = screen.charmModel,
                    onEditClick =  {backstack.add(Screen.CharmEdit)},
                    onCollectionClick = {backstack.add(Screen.Collection)},
                    onHomeClick = {backstack.add(Screen.Home)}
                )
            }
            entry<Screen.ScanQR>{
                ScanQrScreen(
                    onEditClick =  {backstack.add(Screen.CharmEdit)},
                    onCollectionClick = {backstack.add(Screen.Collection)},
                    onHomeClick = {backstack.add(Screen.Home)}
                )
            }
        }
    )

}