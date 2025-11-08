package com.sapienza.reverie.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sapienza.reverie.presentation.theme.ReverieFontFamily
import com.sapienza.reverie.ui.theme.GradientMagCyanInverted
import com.sapienza.reverie.ui.theme.ReverieTheme


@Composable
fun TopBar(
    icon: ImageVector = Icons.Filled.Dashboard,
    title: String = "Reverie",
    modifier: Modifier = Modifier
        .clip(RoundedCornerShape(24.dp))
        .padding(6.dp)
        .shadow(elevation = 6.dp)
) {

    NavigationBar(
        modifier.background(
            brush = Brush.horizontalGradient(
                colors = GradientMagCyanInverted,
                // You can adjust startY and endY for where the gradient starts and ends
            )
        ), containerColor = Color.Transparent
    ) {
        NavigationBarItem(selected = false, onClick = { /*TODO*/ }, icon = { }, label = {

            val size = 30.dp
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {

                Icon(imageVector = icon, contentDescription = null, Modifier.size(size))
                Text(
                    text = title,
                    fontFamily = ReverieFontFamily("Bold"),
                    fontSize = size.value.sp
                )
            }
        }

        )
    }

}

@Preview(showBackground = true)
@Composable
fun TopBarPreview() {
    ReverieTheme {

        TopBar()
    }
}

