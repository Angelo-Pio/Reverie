package com.sapienza.reverie.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.ImageSearch
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.TextFields
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.sapienza.reverie.ui.theme.NavBarColor


@Composable
fun EditBar(
    onCancelClick: () -> Unit = {},
    onCameraClick: () -> Unit = {},
    onTextClick: () -> Unit = {},
    onAddImageClick: () -> Unit = {}

) {
    NavigationBar (
        modifier = Modifier.background(
            brush = Brush.horizontalGradient(
                colors = NavBarColor,
                // You can adjust startY and endY for where the gradient starts and ends
            )
        ),
        containerColor = Color.Transparent
    ){

        NavigationBarItem(
            icon = { Icon(Icons.Filled.PhotoCamera, contentDescription = null) },
            label = { Text("Camera") },
            selected = false,
            onClick = { /*TODO*/ },
            colors = NavigationBarItemDefaults.colors(
                indicatorColor = Color(0xFFb3e6e9),


            )


        )
        NavigationBarItem(
            icon = { Icon(Icons.Filled.TextFields, contentDescription = null) },
            label = { Text("Text") },
            selected = false,
            onClick = onTextClick
        )
        NavigationBarItem(
            icon = { Icon(Icons.Filled.ImageSearch, contentDescription = null) },
            label = { Text("Add Image") },
            selected = false,
            onClick = onAddImageClick
        )
        NavigationBarItem(
            icon = { Icon(Icons.Filled.Cancel, contentDescription = null) },
            label = { Text("Cancel") },
            selected = false,
            onClick = onCancelClick
        )

    }

}

@Preview
@Composable
fun EditBarPreview(){
    EditBar()
}