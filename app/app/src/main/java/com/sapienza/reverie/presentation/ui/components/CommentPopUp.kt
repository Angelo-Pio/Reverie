package com.sapienza.reverie.presentation.ui.components

import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sapienza.reverie.ui.theme.ReverieTheme

@Composable
fun CommentPopUp(
    visible: Boolean,
    onConfirm: (String) -> Unit,
    onCancel: () -> Unit
) {
    // State for the text field content
    var text by remember { mutableStateOf("") }
    val maxChars = 250

    // Animation for the entire pop-up
    AnimatedVisibility(
        visible = visible,
        enter = slideInVertically(initialOffsetY = { it }) + fadeIn(),
        exit = slideOutVertically(targetOffsetY = { it }) + fadeOut()
    ) {
        // A Box to overlay on top of the screen content
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.5f)) // Semi-transparent background
                .padding(16.dp),
            contentAlignment = Alignment.Center // Align the card to the bottom
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(elevation = 8.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("Add a Comment", style = MaterialTheme.typography.titleLarge)
                    Spacer(modifier = Modifier.height(16.dp))

                    OutlinedTextField(
                        value = text,
                        onValueChange = { if (it.length <= maxChars) text = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(120.dp),
                        label = { Text("Your comment") },
                        placeholder = { Text("Write something...") },
                        supportingText = {
                            Text(
                                text = "${text.length} / $maxChars",
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.End,
                            )
                        }
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        TextButton(onClick = {
                            text = "" // Clear text on cancel
                            onCancel()
                        }) {
                            Text("Cancel")
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Button(
                            onClick = {
                                onConfirm(text)
                                text = "" // Clear text on confirm
                            },
                            enabled = text.isNotBlank() // Confirm button is disabled if text is empty
                        ) {
                            Text("Confirm")
                        }
                    }
                }
            }
        }
    }

}

@Preview
@Composable
fun CommentPopUpPreview() {
    ReverieTheme {
        Scaffold(){
            innerPadding ->
            Box(Modifier.padding(innerPadding)){
                CommentPopUp(visible = true, onConfirm = {}, onCancel = {})
            }
        }
    }
}
