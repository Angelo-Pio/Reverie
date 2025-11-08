package com.sapienza.reverie.presentation.ui.components


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Link
import androidx.compose.material.icons.filled.QrCode
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sapienza.reverie.ui.theme.GradientButton
import com.sapienza.reverie.ui.theme.ReverieTheme


enum class ButtonType {
    SHARE,
    QR,
    DONE
}


@Composable
fun CircularButton(
    icon: ImageVector,
    onClick: () -> Unit,
    text: String? = null,
    modifier: Modifier = Modifier
) {
    val buttonSize = 56.dp
    Box(
        modifier = modifier
            .size(buttonSize)
            .clip(CircleShape) // Clip the Box to a circle
            .background(Brush.verticalGradient(colors = GradientButton))

        , // Apply the gradient to the Box
        contentAlignment = Alignment.Center,

    ) {
        Button(
            onClick = onClick,
            shape = CircleShape,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent,
                contentColor = MaterialTheme.colorScheme.onPrimary,

                ),



            contentPadding = PaddingValues(0.dp),
            modifier = Modifier.size(buttonSize),
            //elevation = ButtonDefaults.elevatedButtonElevation(defaultElevation = 5.dp)
            content = {
                Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {


                    Icon(imageVector = icon, contentDescription = null)
                    if (text != null) {
                        Text(text = text ?: "")
                    }
                }
            }

        )
    }
}


@Composable
fun ShareButton(type: ButtonType, modifier: Modifier = Modifier, onClick: () -> Unit = {}) {

    when (type) {
        ButtonType.SHARE -> {
            CircularButton(icon = Icons.Filled.Share, onClick = onClick, modifier = modifier)

        }

        ButtonType.QR -> {
            CircularButton(icon = Icons.Filled.QrCode, onClick = onClick, modifier = modifier)

        }

        ButtonType.DONE -> {
            CircularButton(icon = Icons.Filled.Done, onClick = onClick, modifier = modifier)
        }
    }
}

@Composable
fun ScanButton(onClick: () -> Unit = {}, modifier: Modifier = Modifier) { // Add parameter
    CircularButton(
        icon = Icons.Filled.QrCodeScanner,
        onClick = onClick,
        modifier = modifier
    ) // Pass it down
}

@Composable
fun SaveButton(onClick: () -> Unit = {}, modifier: Modifier = Modifier) { // Add parameter
    CircularButton(icon = Icons.Filled.Save, onClick = onClick, modifier = modifier) // Pass it down
}

@Composable
fun BackButton(onClick: () -> Unit = {}, modifier: Modifier = Modifier) { // Add parameter
    CircularButton(
        icon = Icons.Filled.ArrowBackIosNew,
        onClick = onClick,
        modifier = modifier
    ) // Pass it down
}

@Composable
fun LinkButton(onClick: () -> Unit = {}) { // Add parameter
    CircularButton(icon = Icons.Filled.Link, onClick = onClick) // Pass it down
}


@Preview(showBackground = true)
@Composable
fun ShareButtonPreview() {
    ReverieTheme {

        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            ShareButton(ButtonType.SHARE)
            ShareButton(ButtonType.QR)
            ShareButton(ButtonType.DONE)
            ScanButton(onClick = {})
            BackButton(onClick = {})
            LinkButton(onClick = {})
            SaveButton(onClick = {})
        }
    }

}