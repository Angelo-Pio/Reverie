package com.sapienza.reverie.presentation.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ModeEdit
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.sapienza.reverie.presentation.ui.components.EditBar
import com.sapienza.reverie.presentation.ui.components.SaveButton
import com.sapienza.reverie.presentation.ui.components.TopBar
import com.sapienza.reverie.properties.ApiProperties
import com.sapienza.reverie.ui.theme.ReverieTheme

@Composable
fun CharmEditScreen(
    onCancelClick: () -> Unit = {},
    onSaveClick: () -> Unit = {},
    imageUrl: String
) {
    Scaffold(
        bottomBar = { EditBar(onCancelClick = onCancelClick) },
        topBar = { TopBar(title = "Build your Charm", icon = Icons.Filled.ModeEdit) },
        floatingActionButton = {
            SaveButton(
                modifier = Modifier.padding(bottom = 1.dp, end = 5.dp),
                onClick = onSaveClick
            )
        }) { innerPadding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.Center,
        ) {
            Box(
                modifier = Modifier
                    .size(300.dp)
                    .aspectRatio(2f / 3f),
                contentAlignment = Alignment.Center,
            ) {

                /*Image(
                    painter = painterResource(R.drawable.saint_rick),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize()
                )*/
                AsyncImage(
                        model = imageUrl,
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
            }

        }

    }
}

@Preview
@Composable
fun CharmEditScreenPreview() {
    ReverieTheme {
        CharmEditScreen(imageUrl = ApiProperties.API_FOO_IMAGE_PATH)
    }
}
