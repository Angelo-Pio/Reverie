package com.sapienza.reverie.presentation.ui.screen

import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.VibratorManager
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sapienza.reverie.domain.model.CharmModel
import com.sapienza.reverie.presentation.ui.components.Charm
import com.sapienza.reverie.presentation.util.ShakeDetector
import kotlinx.coroutines.delay

@Composable
fun RevealScreen(
    charmModel: CharmModel,
    onRevealComplete: () -> Unit
) {
    val context = LocalContext.current
    var isRevealed by remember { mutableStateOf(false) }

    // Animation states
    val veilAlpha by animateFloatAsState(
        targetValue = if (isRevealed) 0f else 1f,
        animationSpec = tween(durationMillis = 1000, easing = FastOutSlowInEasing),
        label = "VeilAlpha"
    )
    val pulseScale = remember { Animatable(1f) }

    // Shake Detector Logic
    DisposableEffect(Unit) {
        val shakeDetector = ShakeDetector(context) {
            if (!isRevealed) {
                isRevealed = true // Trigger the reveal on shake
            }
        }
        shakeDetector.start()

        // Clean up the listener when the composable is disposed
        onDispose {
            shakeDetector.stop()
        }
    }

    // Post-reveal animation and navigation logic
    LaunchedEffect(isRevealed) {
        if (isRevealed) {
            // Vibrate the phone
            vibrate(context)

            // Pulse animation
            pulseScale.animateTo(
                targetValue = 1.1f,
                animationSpec = infiniteRepeatable(
                    animation = tween(500),
                    repeatMode = RepeatMode.Reverse
                )
            )

            // Wait for 2 seconds then navigate
            delay(2000)
            onRevealComplete()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ) {
        // The Charm, always present but may be covered
        Charm(
            modifier = Modifier
                .size(300.dp)
                .scale(pulseScale.value),
            charmModel = charmModel
        )

        // The "veil" that covers the charm
        Box(
            modifier = Modifier
                .fillMaxSize()
                .alpha(veilAlpha)
                .background(
                    Brush.radialGradient(
                        colors = listOf(
                            Color.Black.copy(alpha = 0.8f),
                            Color.Black.copy(alpha = 0.95f),
                            Color.Black
                        )
                    )
                ),
            contentAlignment = Alignment.Center
        ) {
            if (!isRevealed) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        "A new charm has been collected!",
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(Modifier.height(16.dp))
                    Text(
                        "Shake your phone to reveal it",
                        color = Color.White.copy(alpha = 0.8f),
                        fontSize = 18.sp
                    )
                }
            }
        }
    }
}

private fun vibrate(context: Context) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        val vibratorManager =
            context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
        val buzzer = vibratorManager.defaultVibrator
        buzzer.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
    }
}