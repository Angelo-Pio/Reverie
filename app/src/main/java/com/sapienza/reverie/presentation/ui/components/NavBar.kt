package com.sapienza.reverie.presentation.ui.components


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Collections
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sapienza.reverie.ui.theme.NavBarColor
import com.sapienza.reverie.ui.theme.ReverieTheme


@Composable
fun NavBar(
    onHomeClick: () -> Unit = {},
    onCollectionClick: () -> Unit = {},
    onEditClick: () -> Unit = {},
    modifier: Modifier = Modifier
        .clip(RoundedCornerShape(24.dp))
        .padding(6.dp)
        .shadow(elevation = 6.dp)
) {
    NavigationBar(
        modifier = modifier.background(
            brush = Brush.horizontalGradient(
                colors = NavBarColor,
                // You can adjust startY and endY for where the gradient starts and ends
            )
        ), containerColor = Color.Transparent
    ) {

        //HOME
        NavigationBarItem(
            icon = { Icon(Icons.Filled.Home, contentDescription = null) },
            label = { Text("Home") },
            selected = false,
            onClick = onHomeClick,
            colors = NavigationBarItemDefaults.colors(

            )


        )
        //EDIT IMAGE
        NavigationBarItem(
            icon = { Icon(Icons.Filled.Create, contentDescription = null) },
            label = { Text("Create") },
            selected = false,
            onClick = onEditClick
        )
        //COLLECTION
        NavigationBarItem(
            icon = { Icon(Icons.Filled.Collections, contentDescription = null) },
            label = { Text("Collection") },
            selected = false,
            onClick = onCollectionClick
        )

    }
}

@Preview
@Composable
fun NavBarPreview() {
    ReverieTheme {

        NavBar()
    }
}