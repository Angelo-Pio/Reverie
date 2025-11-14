package com.sapienza.reverie.presentation.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ModeEdit
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.sapienza.reverie.domain.model.UserModel
import com.sapienza.reverie.presentation.ui.components.EditBar
import com.sapienza.reverie.presentation.ui.components.SaveButton
import com.sapienza.reverie.presentation.ui.components.TopBar
import com.sapienza.reverie.properties.ApiProperties
import com.sapienza.reverie.ui.theme.ReverieTheme

@Composable
fun CharmEditScreen(
    onCancelClick: () -> Unit = {},
    onSaveClick: () -> Unit = {},
    charmImage: String
) {
    Scaffold(bottomBar = { EditBar(onCancelClick = onCancelClick ) }, topBar = { TopBar(title = "Build your Charm", icon = Icons.Filled.ModeEdit) }, floatingActionButton = {SaveButton(modifier = Modifier.padding(bottom = 1.dp, end = 5.dp), onClick = onSaveClick)}){
        innerPadding->

        Box(modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize(), contentAlignment = Alignment.Center){
            AsyncImage(
                model = ApiProperties.API_IMAGES_BASE_PATH+charmImage,
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop)
            Text("here")
        }

    }
}

@Preview
@Composable
fun CharmEditScreenPreview() {
    ReverieTheme {
        CharmEditScreen(charmImage = ApiProperties.API_FOO_IMAGE_PATH)
    }
}
