package com.sapienza.reverie.presentation.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sapienza.reverie.presentation.theme.ReverieFontFamily


@Composable
fun TopBar(modifier: Modifier = Modifier.clip(RoundedCornerShape(24.dp)).padding(6.dp).shadow(elevation = 6.dp)){

    NavigationBar (modifier){
        NavigationBarItem(
            selected = false,
            onClick = { /*TODO*/ },
            icon = {  },

            label = {

                val size = 30.dp
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)){

                Icon(imageVector = Icons.Filled.Dashboard, contentDescription = null, Modifier.size(size))
                Text(
                    text = "Dashboard",
                    fontFamily = ReverieFontFamily("Bold"),
                    fontSize = size.value.sp
                )}
                }

        )
    }

}

@Preview(showBackground = true)
@Composable
fun TopBarPreview(){
    TopBar()
}

