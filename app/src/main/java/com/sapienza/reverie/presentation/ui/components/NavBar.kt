package com.sapienza.reverie.presentation.ui.components


import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Collections
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

private fun RowScope.NavigationBarItem(
    icon: () -> Unit,
    label: () -> Unit,
    selected: Boolean,
    onClick: () -> Unit,
    colors: Any,
    label2: () -> Unit,
    selected2: Boolean,
    onClick2: () -> Unit
) {
}

@Composable
fun NavBar(modifier: Modifier = Modifier.clip(RoundedCornerShape(24.dp)).padding(6.dp).shadow(elevation = 6.dp)){
    NavigationBar (modifier = modifier){

        NavigationBarItem(
            icon = { Icon(Icons.Filled.Home, contentDescription = null) },
            label = { Text("Home") },
            selected = true,
            onClick = { /*TODO*/ },
            colors = NavigationBarItemDefaults.colors(
                indicatorColor = Color(0xFFb3e6e9)

            )


        )
        NavigationBarItem(
            icon = { Icon(Icons.Filled.Create, contentDescription = null) },
            label = { Text("Create") },
            selected = false,
            onClick = { /*TODO*/ }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Filled.Collections, contentDescription = null) },
            label = { Text("Collection") },
            selected = false,
            onClick = { /*TODO*/ }
        )

    }
}

@Preview
@Composable
fun NavBarPreview(){
    NavBar()
}