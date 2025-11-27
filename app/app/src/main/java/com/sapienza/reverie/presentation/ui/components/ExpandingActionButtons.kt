package com.sapienza.reverie.presentation.ui.components

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.sapienza.reverie.domain.model.CharmModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun ExpandingActionButtons(
    modifier: Modifier = Modifier,
    isExpanded: Boolean,
    onExpandToggle: () -> Unit,
    onQRShareClick: () -> Unit = {},
    onLinkShareClick: () -> Unit = {}
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp) // Space between the buttons
    ) {
        // This is a container for the two hidden buttons
        val animationSpec = tween<Float>(durationMillis = 300)
        val animationSpecDp = tween<Dp>(durationMillis = 300)

        // --- Animation States ---

        // 1. Animate Alpha (transparency) for both buttons
        // Buttons will be fully visible when expanded, and invisible when not.
        val buttonsAlpha by animateFloatAsState(
            targetValue = if (isExpanded) 1f else 0f,
            animationSpec = animationSpec,
            label = "Buttons Alpha"
        )

        // 2. Animate the vertical offset (Y position) for the buttons.
        // This makes them appear to come from "under" the main button.
        val buttonsOffsetY by animateDpAsState(
            targetValue = if (isExpanded) 60.dp else 0.dp, // 60.dp below the main button
            animationSpec = animationSpecDp, label = "Buttons Y Offset"
        )

        // 3. Animate the horizontal offset (X position) for the *second* button.
        // The first button stays aligned, the second moves to the right.
        val secondButtonOffsetX by animateDpAsState(
            targetValue = if (isExpanded) 65.dp else 0.dp, // 50.dp to the right
            animationSpec = animationSpecDp, label = "Second Button X Offset"
        )

        // --- Layout ---

        // Use a Box to layer the buttons on top of each other.
        // The main button will be at the bottom, so it's drawn first (and appears on top).
        Box(
            modifier = modifier, contentAlignment = Alignment.TopCenter
            // Align buttons to the top-center of the Box
        ) {
            // --- The Two Hidden Buttons ---

            // QR Button (the first one to appear)
            ShareButton(
                modifier = Modifier
                    .offset(y = buttonsOffsetY) // Apply the vertical animation
                    .alpha(buttonsAlpha),       // Apply the fade animation
                type = ButtonType.QR, onClick = onQRShareClick
            )

            // Link Button (the second one, moving horizontally)
            ShareButton(
                modifier = Modifier
                    .offset(
                        x = secondButtonOffsetX, y = buttonsOffsetY
                    ) // Apply BOTH offsets
                    .alpha(buttonsAlpha),                              // Apply the fade animation
                type = ButtonType.Link, onClick = onLinkShareClick
            )

            // --- The Main, Static Button ---
            // This button is always visible and does not have any offset or alpha animations.
            // It stays in place.
            ShareButton(
                type = ButtonType.SHARE,
                modifier = Modifier.size(45.dp),
                onClick = onExpandToggle // This toggles the 'isExpanded' state
            )
        }

        // This is the main ShareButton that is always visible

    }
}

@Composable
fun ExpandingInfoButton(
    modifier: Modifier = Modifier,
    isExpanded: Boolean,
    onExpandToggle: () -> Unit,
    charmModel: CharmModel
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        var collected_in: String = ""
        if (charmModel.collected_in != null) {
            collected_in = "in ${charmModel.collected_in}"
        }

        // --- NEW: Dialog visibility state ---
        var showDialog by remember { mutableStateOf(false) }

        // --- SHOW DIALOG ONLY WHEN REQUESTED ---
        if (showDialog) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                confirmButton = {
                    TextButton(onClick = { showDialog = false }) {
                        Text("Close")
                    }
                },
                title = { Text("Charm Details") },
                text = {
                    Column {
                        Text("Charm created by ${charmModel.creator}")
                        Text("on ${formatIsoToPretty(charmModel.created_at!!)}\n")
                        Text(collected_in)
                    }
                }
            )
        }

        val animationSpec = tween<Float>(durationMillis = 300)
        val animationSpecDp = tween<Dp>(durationMillis = 300)

        val buttonsAlpha by animateFloatAsState(
            targetValue = if (isExpanded) 1f else 0f,
            animationSpec = animationSpec,
            label = "Buttons Alpha"
        )

        val buttonsOffsetY by animateDpAsState(
            targetValue = if (isExpanded) 60.dp else 0.dp,
            animationSpec = animationSpecDp,
            label = "Buttons Y Offset"
        )

        Box(modifier = modifier) {

            CircularButton(
                icon = Icons.Filled.Info,
                modifier = Modifier.align(Alignment.BottomEnd),
                onClick = {
                    showDialog = true   // <-- SHOW DIALOG HERE
                }
            )
        }
    }
}


fun formatIsoToPretty(dateString: String): String {
    val inputFormatter = DateTimeFormatter.ISO_DATE_TIME
    val date = LocalDateTime.parse(dateString, inputFormatter)

    val outputFormatter = DateTimeFormatter.ofPattern("EEEE dd MMMM", Locale.ENGLISH)
    return date.format(outputFormatter)
}