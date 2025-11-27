package com.sapienza.reverie.presentation.ui.screen

import android.graphics.Bitmap
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ModeEdit
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Precision
import com.sapienza.reverie.presentation.ui.components.CharmDescriptionDialog
import com.sapienza.reverie.presentation.ui.components.DraggableOverlayImage
import com.sapienza.reverie.presentation.ui.components.DraggableOverlayText
import com.sapienza.reverie.presentation.ui.components.EditBar
import com.sapienza.reverie.presentation.ui.components.SaveButton
import com.sapienza.reverie.presentation.ui.components.Sticker
import com.sapienza.reverie.presentation.ui.components.TopBar
import com.sapienza.reverie.presentation.util.CaptureBitmap
import com.sapienza.reverie.presentation.viewmodel.CharmViewModel
import com.sapienza.reverie.presentation.viewmodel.SessionViewModel
import com.sapienza.reverie.properties.ApiProperties
import com.sapienza.reverie.ui.theme.ReverieTheme
import dev.shreyaspatil.capturable.controller.rememberCaptureController

@Composable
fun CharmEditScreen(
    onCancelClick: () -> Unit = {}, onSaveClick: () -> Unit = {}, onAddImageClick: () -> Unit = {}, imageUrl: String
) {
    val charmViewModel: CharmViewModel = viewModel()
    val sessionViewModel : SessionViewModel = viewModel()
    val items = charmViewModel.overlayItems
    val context = LocalContext.current

    // Controller to trigger the bitmap capture
    val captureController = rememberCaptureController()

    // State to manage the description dialog
    var showDescriptionDialog by remember { mutableStateOf(false) }
    // State to hold the captured bitmap temporarily
    var capturedBitmap by remember { mutableStateOf<Bitmap?>(null) }

    // When the bitmap is successfully captured, trigger the dialog
    if (capturedBitmap != null && !showDescriptionDialog) {
        showDescriptionDialog = true
    }

    // This composable will be displayed when its state is true
    if (showDescriptionDialog) {
        CharmDescriptionDialog(
            onDismiss = {
                showDescriptionDialog = false
                capturedBitmap = null // Reset state to allow another capture
            },
            onConfirm = { description ->
                capturedBitmap?.let { bitmap ->

                    charmViewModel.createCharm(context, sessionViewModel.user.value!!.id, bitmap, description)
                }
                showDescriptionDialog = false
                capturedBitmap = null
                onSaveClick() // Execute the navigation action passed to the screen
            }
        )
    }

    Scaffold(
        bottomBar = {
            EditBar(
                onCancelClick = onCancelClick,
                onTextClick = {
                    items.add(
                        Sticker.TextItem(
                            id = System.currentTimeMillis(), text = "New Text"
                        )
                    )
                },
                onAddImageClick = onAddImageClick
            )
        },
        topBar = { TopBar(title = "Build your Charm", icon = Icons.Filled.ModeEdit) },
        floatingActionButton = {
            SaveButton(
                modifier = Modifier.padding(bottom = 1.dp, end = 5.dp),
                onClick = {

                    captureController.capture()
                }
            )
        }) { innerPadding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.Center,
        ) {
            // Wrap the content you want to capture with the CaptureBitmap composable
            CaptureBitmap(
                captureController = captureController,
                onBitmapCaptured = { bitmap ->
                    capturedBitmap = bitmap
                }
            ) {
                // This Box now has padding to avoid cropping the stickers
                Box(modifier = Modifier.padding(50.dp)) {
                    Box(
                        modifier = Modifier
                            .size(300.dp)
                            .aspectRatio(2f / 3f),
                        contentAlignment = Alignment.Center,
                    ) {
                        val imageRequest = ImageRequest.Builder(LocalContext.current)
                            .data(imageUrl)
                            .crossfade(true)
                            // This is the key change: tells Coil to load the image at its original size
                            .precision(Precision.EXACT)
                            .build()

                        AsyncImage(
                            model = imageRequest,
                            contentDescription = null,
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )

                        // Your existing logic for displaying stickers remains unchanged
                        items.forEachIndexed { index, item ->
                            when (item) {
                                is Sticker.TextItem -> {
                                    DraggableOverlayText(
                                        item = item,
                                        onTransform = { pan, zoom, rotation ->
                                            val currentItem = charmViewModel.overlayItems[index] as Sticker.TextItem
                                            val updatedSticker = currentItem.copy(
                                                offsetX = currentItem.offsetX + pan.x,
                                                offsetY = currentItem.offsetY + pan.y,
                                                scale = currentItem.scale * zoom,
                                                rotation = currentItem.rotation + rotation
                                            )
                                            charmViewModel.updateSticker(index, updatedSticker)
                                        },
                                        onTextChange = { newText, newColor ->
                                            val currentItem = charmViewModel.overlayItems[index] as Sticker.TextItem

                                            charmViewModel.updateSticker(index, currentItem.copy(text = newText, color = newColor))
                                        }
                                    )
                                }
                                is Sticker.ImageItem -> {
                                    DraggableOverlayImage(
                                        item = item,
                                        onTransform = { pan, zoom, rotation ->
                                            val currentItem = charmViewModel.overlayItems[index] as Sticker.ImageItem
                                            val updatedSticker = currentItem.copy(
                                                offsetX = currentItem.offsetX + pan.x,
                                                offsetY = currentItem.offsetY + pan.y,
                                                scale = currentItem.scale * zoom,
                                                rotation = currentItem.rotation + rotation
                                            )
                                            charmViewModel.updateSticker(index, updatedSticker)
                                        }
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun CharmEditScreenPreview() {
    ReverieTheme {
        CharmEditScreen(imageUrl = ApiProperties.API_FOO_IMAGE_PATH)
    }
}
