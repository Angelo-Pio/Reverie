package com.sapienza.reverie.presentation.ui.navigation


import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import com.sapienza.reverie.presentation.ui.components.Sticker
import com.sapienza.reverie.presentation.ui.screen.CharmEditScreen
import com.sapienza.reverie.presentation.ui.screen.CharmScreen
import com.sapienza.reverie.presentation.ui.screen.CollectionScreen
import com.sapienza.reverie.presentation.ui.screen.DashboardScreen
import com.sapienza.reverie.presentation.ui.screen.LoginScreen
import com.sapienza.reverie.presentation.ui.screen.RevealScreen
import com.sapienza.reverie.presentation.ui.screen.ScanQrScreen
import com.sapienza.reverie.presentation.ui.screen.SearchImageScreen
import com.sapienza.reverie.presentation.ui.screen.ShareQrScreen
import com.sapienza.reverie.presentation.ui.screen.SignUpScreen
import com.sapienza.reverie.presentation.viewmodel.CharmViewModel
import com.sapienza.reverie.presentation.viewmodel.SessionViewModel


@Composable
fun AppNavigation() {

    val backstack = remember { mutableStateListOf<Screen>(Screen.Login) }
    val sessionViewModel: SessionViewModel = viewModel()
    val charmViewModel: CharmViewModel = viewModel()





    NavDisplay(
        backStack = backstack,

        entryProvider = entryProvider {
            entry<Screen.Login> {
                LoginScreen(
                    onLoginClick = { backstack.add(Screen.Home) },
                    onSignUpClick = { backstack.add(Screen.SignUp) },

                    onGoogleSignInClick = { backstack.add(Screen.Home) }
                )
            }
            entry<Screen.SignUp> {
                SignUpScreen(
                    onSignUpSuccess = { backstack.add(Screen.Home) },
                    onLoginClick = { backstack.removeAt(backstack.lastIndex) }

                )
            }
            entry<Screen.Home> {
                DashboardScreen(
                    onScanClick = { backstack.add(Screen.ScanQR) }, // TODO : this should redirect to a screen used to scan a QR code not share it !
                    onEditClick = { backstack.add(Screen.SearchMainImage) },
                    onLogOutClick = {
                        charmViewModel.clearAllData()
                        sessionViewModel.logout()
                        backstack.clear()
                        backstack.add(Screen.Login)
                    },
                    onCollectionClick = { backstack.add(Screen.Collection) },
                    onCharmClick = { charm -> backstack.add(Screen.Charm(charm)) }
                )
            }
            entry<Screen.SearchMainImage> {
                SearchImageScreen(
                    onHomeClick = { backstack.add(Screen.Home) },
                    onImageClick = { charmImage -> backstack.add(Screen.CharmEdit(charmImage)) },
                )
            }
            entry<Screen.CharmEdit> { screen ->
                CharmEditScreen(
                    imageUrl = screen.image,
                    onCancelClick = { backstack.add(Screen.Home) },
                    onSaveClick = { backstack.add(Screen.Collection) },
                    onAddImageClick = { backstack.add(Screen.SearchImage) }
                )

            }
            entry<Screen.SearchImage> {
                SearchImageScreen(
                    onHomeClick = { backstack.removeAt(backstack.lastIndex) },
                    onImageClick = { charmImage ->
                        charmViewModel.addSticker(
                            Sticker.ImageItem(
                                id = System.currentTimeMillis(), imageUrl = charmImage
                            )
                        );
                        backstack.removeAt(backstack.lastIndex)
                    },
                )
            }
            entry<Screen.Collection> {
                CollectionScreen(
                    onCharmClick = { charmModel -> backstack.add(Screen.Charm(charmModel)) },
                    onEditClick = { backstack.add(Screen.SearchMainImage) },
                    onHomeClick = { backstack.add(Screen.Home) },
                    onCollectionClick = { backstack.add(Screen.Collection) },
                )
            }
            entry<Screen.Charm> { screen ->
                CharmScreen(
                    charmModel = screen.charmModel,
                    onCollectionClick = { backstack.add(Screen.Collection) },
                    onHomeClick = { backstack.add(Screen.Home) },
                    onQRShareClick = { charm_id -> backstack.add(Screen.ShareQr(charm_id = charm_id)) },

                    )
            }
            entry<Screen.ShareQr> {
                //TODO: this will need an argument to generate the QR code from a link
                    screen ->

                ShareQrScreen(
                    onHomeClick = { backstack.add(Screen.Home) },
                    onCollectionClick = { backstack.add(Screen.Collection) },
                    charmId = screen.charm_id
                )

            }
            entry<Screen.ScanQR> {

                ScanQrScreen(
                    onHomeClick = { backstack.add(Screen.Home) },
                    onCollectionClick = { backstack.add(Screen.Collection) },
                    // The callback now directly gives us the charm to reveal
                    onCharmCollected = { collectedCharm ->
                        backstack.removeAt(backstack.lastIndex) // Remove ScanQrScreen
                        backstack.add(Screen.Reveal(collectedCharm)) // Add RevealScreen
                    }
                )
            }


            entry<Screen.Reveal> { screen ->
                RevealScreen(
                    charmModel = screen.charm,
                    onRevealComplete = {
                        backstack.remove(screen)
                        backstack.add(Screen.Collection)
                    }
                )
            }
        }
    )

}