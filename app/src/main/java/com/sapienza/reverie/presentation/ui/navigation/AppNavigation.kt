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
import com.sapienza.reverie.presentation.ui.screen.SearchImageScreen


@Composable
fun AppNavigation() {

    val backstack = remember { mutableStateListOf<Screen>(Screen.Login) }

    NavDisplay(
        backStack = backstack,

        entryProvider = entryProvider {
            entry<Screen.Login> {
                LoginScreen(onLoginClick = { backstack.add(Screen.Home) })
            }
            entry<Screen.Home> {
                DashboardScreen(
                    onScanClick = { backstack.add(Screen.ScanQR) }, // TODO : this should redirect to a screen used to scan a QR code not share it !
                    onEditClick = { backstack.add(Screen.SearchImage) },
                    onHomeClick = { backstack.add(Screen.Home) },
                    onCollectionClick = { backstack.add(Screen.Collection) }

                )
            }
            entry<Screen.CharmEdit> {
                //TODO: search implementation, saving image etc.
                CharmEditScreen(
                    onCancelClick = { backstack.add(Screen.Home) },
                    onSaveClick = {backstack.add(Screen.Collection)}
                )
            }
            entry<Screen.Collection> {
                CollectionScreen(
                    onCharmClick = { charmModel -> backstack.add(Screen.Charm(charmModel)) },
                    onEditClick = { backstack.add(Screen.SearchImage) },
                    onHomeClick = { backstack.add(Screen.Home) },
                    onCollectionClick = { backstack.add(Screen.Collection) },
                )
            }
            entry<Screen.Charm> { screen ->
                CharmScreen(
                    charmModel = screen.charmModel,
                    onEditClick = { backstack.add(Screen.SearchImage) },
                    onCollectionClick = { backstack.add(Screen.Collection) },
                    onHomeClick = { backstack.add(Screen.Home) },
                    onQRShareClick  = {backstack.add(Screen.ScanQR)},
                    //onLinkShareClick : () -> Unit = {} TODO: implement link sharing (copy on clipboard)
                )
            }
            entry<Screen.ScanQR> {
                //TODO: this will need an argument to generate the QR code from a link
                ScanQrScreen(
                    onEditClick = { backstack.add(Screen.SearchImage) },
                    onCollectionClick = { backstack.add(Screen.Collection) },
                    onHomeClick = { backstack.add(Screen.Home) }
                )
            }
            entry<Screen.SearchImage> {
                /*TODO: search is used for take first background picture and add stickers, this should be impleemented
                * TODO:  selected image should be passed to edit screen
                * */

                SearchImageScreen(
                    onHomeClick = { backstack.add(Screen.Home) },
                    onImageClick = { backstack.add(Screen.CharmEdit) }
                )
            }
        }
    )

}