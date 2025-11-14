package com.sapienza.reverie.presentation.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import kotlin.math.roundToInt

sealed class Sticker {
    data class TextItem(
        val id: Long,
        val text: String,
        val color: Color = Color.White, // 1. Add color property with a default value
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

@Composable
fun DraggableOverlayText(
    item: Sticker.TextItem,
    onTransform: (pan: androidx.compose.ui.geometry.Offset, zoom: Float, rotation: Float) -> Unit,
    onTextChange: (String, Color) -> Unit // 2. Update the onTextChange lambda
) {
    var showDialog by remember { mutableStateOf(false) }

    if (showDialog) {
        EditTextDialog(
            initialText = item.text,
            initialColor = item.color, // 3. Pass the current color to the dialog
            onConfirm = { newText, newColor ->
                onTextChange(newText, newColor) // Pass both new values back
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
        color = item.color, // 4. Use the color from the item
        modifier = Modifier
            .offset { IntOffset(item.offsetX.roundToInt(), item.offsetY.roundToInt()) }
            .graphicsLayer(
                scaleX = item.scale,
                scaleY = item.scale,
                rotationZ = item.rotation
            )
            .pointerInput(Unit) {
                detectTransformGestures { _, pan, zoom, rotation ->
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
                    onTransform(pan, zoom, rotation)
                }
            }
    )
}


@Composable
fun EditTextDialog(
    initialText: String,
    initialColor: Color, // 5. Accept an initial color
    onConfirm: (String, Color) -> Unit, // Update the onConfirm lambda
    onDismiss: () -> Unit
) {
    var text by remember { mutableStateOf(initialText) }
    var selectedColor by remember { mutableStateOf(initialColor) } // State for the selected color

    val availableColors = listOf(Color.White, Color.Black, Color.Red, Color.Green, Color.Blue, Color.Yellow, Color.Cyan, Color.Magenta)

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Edit Text") },
        text = {
            Column {
                TextField(
                    value = text,
                    onValueChange = { text = it }
                )
                Spacer(modifier = Modifier.height(16.dp))
                // 6. Add a row of color pickers
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    availableColors.forEach { color ->
                        ColorPickerItem(
                            color = color,
                            isSelected = color == selectedColor,
                            onClick = { selectedColor = color }
                        )
                    }
                }
            }
        },
        confirmButton = {
            TextButton(onClick = { onConfirm(text, selectedColor) }) { // Pass both values back
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

@Composable
private fun ColorPickerItem(color: Color, isSelected: Boolean, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(32.dp)
            .clip(CircleShape)
            .background(color)
            .clickable(onClick = onClick)
            .border(
                BorderStroke(if (isSelected) 2.dp else 0.dp, Color.Gray),
                CircleShape
            )
    )
}
