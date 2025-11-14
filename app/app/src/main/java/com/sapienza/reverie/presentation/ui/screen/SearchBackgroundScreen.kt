package com.sapienza.reverie.presentation.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sapienza.reverie.R
import com.sapienza.reverie.presentation.ui.components.GoogleSearchBar
import com.sapienza.reverie.properties.ApiProperties
import com.sapienza.reverie.ui.theme.NavBarColor
import com.sapienza.reverie.ui.theme.ReverieTheme

@Composable
fun SearchImageScreen(
    modifier: Modifier = Modifier,
    onHomeClick: () -> Unit = {},
    onImageClick: (image: String) -> Unit = {}
) {

    Scaffold(
        bottomBar = {
            Box(Modifier
                .clip(RoundedCornerShape(24.dp))
                .padding(6.dp)
                .shadow(elevation = 6.dp)) {
                NavigationBar(
                    modifier = modifier.background(brush = Brush.horizontalGradient(colors = NavBarColor)),
                    containerColor = Color.Transparent,
                    content = {
                        NavigationBarItem(
                            icon = { Icon(Icons.Filled.Cancel, contentDescription = null) },
                            label = { Text("Cancel") },
                            selected = false,
                            onClick = onHomeClick,
                            colors = NavigationBarItemDefaults.colors(

                            )


                        )

                    }
                )
            }
        })
    { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            GoogleSearchBar()
            Box(Modifier
                .padding(13.dp)
                .aspectRatio(2f / 3f)) {

                Image(
                    painter = painterResource(R.drawable.saint_rick),
                    contentDescription = "Selectable background image",
                    modifier = Modifier
                        .fillMaxSize() // Make the image fill the Box
                        .clickable(onClick = { onImageClick(ApiProperties.API_FOO_IMAGE_PATH) }), // 2. Add the clickable modifier here
                    contentScale = ContentScale.Crop // Ensure the image covers the area
                )



            }

        }

    }


}

@Preview
@Composable
fun SearchBackgroundScreenPreview() {
    ReverieTheme {
        SearchImageScreen()
    }
}
