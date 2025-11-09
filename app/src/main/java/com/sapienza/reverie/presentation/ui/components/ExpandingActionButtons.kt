package com.sapienza.reverie.presentation.ui.components

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun ExpandingActionButtons(
    modifier: Modifier = Modifier,
    isExpanded: Boolean,
    onExpandToggle: () -> Unit ,
    onQRShareClick : () -> Unit = {},
    onLinkShareClick : () -> Unit = {}
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
            animationSpec = animationSpecDp,
            label = "Buttons Y Offset"
        )

        // 3. Animate the horizontal offset (X position) for the *second* button.
        // The first button stays aligned, the second moves to the right.
        val secondButtonOffsetX by animateDpAsState(
            targetValue = if (isExpanded) 65.dp else 0.dp, // 50.dp to the right
            animationSpec = animationSpecDp,
            label = "Second Button X Offset"
        )

        // --- Layout ---

        // Use a Box to layer the buttons on top of each other.
        // The main button will be at the bottom, so it's drawn first (and appears on top).
        Box(
            modifier = modifier,
            contentAlignment = Alignment.TopCenter
            // Align buttons to the top-center of the Box
        ) {
            // --- The Two Hidden Buttons ---

            // QR Button (the first one to appear)
            ShareButton(
                modifier = Modifier
                    .offset(y = buttonsOffsetY) // Apply the vertical animation
                    .alpha(buttonsAlpha),       // Apply the fade animation
                type = ButtonType.QR,
                onClick = onQRShareClick
            )

            // Link Button (the second one, moving horizontally)
            ShareButton(
                modifier = Modifier
                    .offset(x = secondButtonOffsetX, y = buttonsOffsetY) // Apply BOTH offsets
                    .alpha(buttonsAlpha),                              // Apply the fade animation
                type = ButtonType.Link,
                onClick = onLinkShareClick
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