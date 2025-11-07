package com.sapienza.reverie.presentation.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Collections
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.TextFields
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview


@Composable
fun EditBar() {
    NavigationBar (){

        NavigationBarItem(
            icon = { Icon(Icons.Filled.PhotoCamera, contentDescription = null) },
            label = { Text("Camera") },
            selected = true,
            onClick = { /*TODO*/ },
            colors = NavigationBarItemDefaults.colors(
                indicatorColor = Color(0xFFb3e6e9)

            )


        )
        NavigationBarItem(
            icon = { Icon(Icons.Filled.TextFields, contentDescription = null) },
            label = { Text("Text") },
            selected = false,
            onClick = { /*TODO*/ }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Filled.Search, contentDescription = null) },
            label = { Text("Search") },
            selected = false,
            onClick = { /*TODO*/ }
        )

    }

}

@Preview
@Composable
fun EditBarPreview(){
    EditBar()
}