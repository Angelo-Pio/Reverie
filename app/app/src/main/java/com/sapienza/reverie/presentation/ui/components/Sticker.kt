package com.sapienza.reverie.presentation.ui.components

import android.R.attr.onClick
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.sp
import androidx.xr.runtime.math.toDegrees
import coil.compose.AsyncImage
import kotlin.math.PI

sealed class Sticker {
    data class TextItem(
        val id: Long,
        val text: String,
        var scale: Float = 1f,
        var rotation: Float = 0f,
        var offsetX: Float = 0f,
        var offsetY: Float = 0f
    ) : Sticker()

    data class ImageItem(
        val id: Long,
        val imageUrl: String,
        var scale: Float = 1f,
        var rotation: Float = 0f,
        var offsetX: Float = 0f,
        var offsetY: Float = 0f
    ) : Sticker()
}

@Composable
fun DraggableOverlayText(
    item: Sticker.TextItem,
    onTextChange: (String) -> Unit
) {
    var offsetX by remember { mutableStateOf(item.offsetX) }
    var offsetY by remember { mutableStateOf(item.offsetY) }
    var rotationDeg by remember { mutableStateOf(item.rotation) }
    var scale by remember { mutableStateOf(item.scale) }

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
        fontSize = (22.sp * scale),
        color = Color.White,
        modifier = Modifier
            .offset { IntOffset(offsetX.toInt(), offsetY.toInt()) }
            .graphicsLayer(
                scaleX = scale,
                scaleY = scale,
                rotationZ = rotationDeg
            )
            .pointerInput(Unit) {
                detectTransformGestures { _, pan, zoom, rotationRadians ->
                    offsetX += pan.x
                    offsetY += pan.y
                    scale *= zoom
                    rotationDeg += Math.toDegrees(rotationRadians.toDouble()).toFloat()
                }
            }
            .clickable {
                showDialog = true
            }
    )
}

@Composable
fun DraggableOverlayImage(item: Sticker.ImageItem) {

    var offsetX by remember { mutableStateOf(item.offsetX) }
    var offsetY by remember { mutableStateOf(item.offsetY) }
    var rotationDeg by remember { mutableStateOf(item.rotation) }
    var scale by remember { mutableStateOf(item.scale) }

    AsyncImage(
        model = item.imageUrl,
        contentDescription = null,
        modifier = Modifier
            .offset { IntOffset(offsetX.toInt(), offsetY.toInt()) }
            .graphicsLayer(
                scaleX = scale,
                scaleY = scale,
                rotationZ = rotationDeg
            )
            .pointerInput(Unit) {
                detectTransformGestures { _, pan, zoom, rotationRadians ->
                    offsetX += pan.x
                    offsetY += pan.y
                    scale *= zoom
                    val deltaDeg = Math.toDegrees(rotationRadians.toDouble()).toFloat()
                    rotationDeg += deltaDeg
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

