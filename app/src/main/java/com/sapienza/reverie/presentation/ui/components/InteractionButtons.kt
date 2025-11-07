package com.sapienza.reverie.presentation.ui.components


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


enum class ButtonType {
    LINK,
    QR,
    DONE
}


@Composable
fun CircularButton(icon: ImageVector, onClick: () -> Unit, text: String? = null) {
    val buttonSize = 56.dp
    Button(
        onClick = onClick,
        shape = CircleShape,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.White,
            contentColor = Color.Black,

            ),
        border = BorderStroke(width = 1.dp, color = Color.Black),
        contentPadding = PaddingValues(0.dp),
        modifier = Modifier.size(buttonSize)


    ) {


        Column(horizontalAlignment = Alignment.CenterHorizontally) {


            Icon(imageVector = icon, contentDescription = null)
            if (text != null) {
                Text(text = text ?: "")
            }
        }

    }
}


@Composable
fun ShareButton(type: ButtonType) {

    when (type) {
        ButtonType.LINK -> {
            CircularButton(icon = Icons.Filled.Share, onClick = {})

        }

        ButtonType.QR -> {
            CircularButton(icon = Icons.Filled.QrCode, onClick = {})

        }

        ButtonType.DONE -> {
            CircularButton(icon = Icons.Filled.Done, onClick = {})
        }
    }
}
@Composable
fun ScanButton(onClick: () -> Unit) { // Add parameter
    CircularButton(icon = Icons.Filled.QrCodeScanner, onClick = onClick, text = "Scan") // Pass it down
}

@Composable
fun SaveButton(onClick: () -> Unit) { // Add parameter
    CircularButton(icon = Icons.Filled.Save, onClick = onClick) // Pass it down
}

@Composable
fun BackButton(onClick: () -> Unit) { // Add parameter
    CircularButton(icon = Icons.Filled.ArrowBackIosNew, onClick = onClick) // Pass it down
}

@Composable
fun LinkButton(onClick: () -> Unit) { // Add parameter
    CircularButton(icon = Icons.Filled.Link, onClick = onClick) // Pass it down
}



@Preview(showBackground = true)
@Composable
fun ShareButtonPreview() {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        ShareButton(ButtonType.LINK)
        ShareButton(ButtonType.QR)
        ShareButton(ButtonType.DONE)
        ScanButton(onClick = {})
        BackButton(onClick = {})
        LinkButton(onClick = {})
    }

}