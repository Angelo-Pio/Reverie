package com.sapienza.reverie.presentation.util

import android.graphics.Bitmap
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.asAndroidBitmap // Import the extension function
import dev.shreyaspatil.capturable.Capturable
import dev.shreyaspatil.capturable.controller.CaptureController

/**
 * A utility composable that allows capturing its content as a Bitmap.
 *
 * @param captureController The controller to trigger the capture.
 * @param onBitmapCaptured The callback to be invoked when the bitmap is successfully captured.
 * @param content The composable content to be captured.
 */
@Composable
fun CaptureBitmap(
    captureController: CaptureController,
    onBitmapCaptured: (Bitmap) -> Unit,
    content: @Composable () -> Unit
) {
    Capturable(
        controller = captureController,
        onCaptured = { imageBitmap, error ->
            if (imageBitmap != null) {
                // Convert the ImageBitmap to an Android Bitmap before passing it to the callback
                onBitmapCaptured(imageBitmap.asAndroidBitmap())
            }
            if (error != null) {
                // Handle error
                error.printStackTrace()
            }
        }
    ) {
        content()
    }
}
