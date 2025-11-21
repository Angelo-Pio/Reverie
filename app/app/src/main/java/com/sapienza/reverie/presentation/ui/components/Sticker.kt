package com.sapienza.reverie.presentation.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.offset
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import kotlin.math.roundToInt

sealed class Sticker {
    data class TextItem(
        val id: Long,
        val text: String,
        val scale: Float = 1f,
        val rotation: Float = 0f,
        val offsetX: Float = 0f,
        val offsetY: Float = 0f
    ) : Sticker()

    data class ImageItem(
        val id: Long,
        val imageUrl: String,
        val scale: Float = 1f,
        val rotation: Float = 0f,
        val offsetX: Float = 0f,
        val offsetY: Float = 0f
    ) : Sticker()
}

// In app/src/main/java/com/sapienza/reverie/presentation/ui/components/Sticker.kt

@Composable
fun DraggableOverlayText(
    item: Sticker.TextItem,
    // Change the signature to accept deltas
    onTransform: (pan: androidx.compose.ui.geometry.Offset, zoom: Float, rotation: Float) -> Unit,
    onTextChange: (String) -> Unit
) {
    var showDialog by remember { mutableStateOf(false) }

    if (showDialog) {
        EditTextDialog(
            initialText = item.text,
            onConfirm = {
                onTextChange(it)
                showDialog = false
            },
            onDismiss = {
                showDialog = false
            }
        )
    }

    Text(
        text = item.text,
        fontSize = (22.sp * item.scale),
        color = Color.White,
        modifier = Modifier
            .offset { IntOffset(item.offsetX.roundToInt(), item.offsetY.roundToInt()) }
            .graphicsLayer(
                scaleX = item.scale,
                scaleY = item.scale,
                rotationZ = item.rotation
            )
            .pointerInput(Unit) {
                detectTransformGestures { _, pan, zoom, rotation ->
                    // Pass the deltas directly
                    onTransform(pan, zoom, rotation)
                }
            }
            .clickable {
                showDialog = true
            }
    )
}

@Composable
fun DraggableOverlayImage(
    item: Sticker.ImageItem,
    // Change the signature to accept deltas
    onTransform: (pan: androidx.compose.ui.geometry.Offset, zoom: Float, rotation: Float) -> Unit
) {
    AsyncImage(
        model = item.imageUrl,
        contentDescription = null,
        modifier = Modifier
            .offset { IntOffset(item.offsetX.roundToInt(), item.offsetY.roundToInt()) }
            .graphicsLayer(
                scaleX = item.scale,
                scaleY = item.scale,
                rotationZ = item.rotation
            )
            .pointerInput(Unit) {
                detectTransformGestures { _, pan, zoom, rotation ->
                    // Pass the deltas directly
                    onTransform(pan, zoom, rotation)
                }
            }
    )
}


@Composable
fun EditTextDialog(
    initialText: String,
    onConfirm: (String) -> Unit,
    onDismiss: () -> Unit
) {
    var text by remember { mutableStateOf(initialText) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Edit Text") },
        text = {
            TextField(
                value = text,
                onValueChange = { text = it }
            )
        },
        confirmButton = {
            TextButton(onClick = { onConfirm(text) }) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}
