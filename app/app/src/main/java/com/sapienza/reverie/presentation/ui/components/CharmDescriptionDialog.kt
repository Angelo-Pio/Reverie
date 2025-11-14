package com.sapienza.reverie.presentation.ui.components


import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

@Composable
fun CharmDescriptionDialog(
    onDismiss: () -> Unit,
    onConfirm: (description: String) -> Unit
) {
    var description by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Describe your Charm") },
        text = {
            TextField(
                value = description,
                onValueChange = { description = it },
                placeholder = { Text("e.g., My amazing creation") }
            )
        },
        confirmButton = {
            TextButton(
                onClick = { onConfirm(description) },
                enabled = description.isNotBlank()
            ) {
                Text("Save")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}