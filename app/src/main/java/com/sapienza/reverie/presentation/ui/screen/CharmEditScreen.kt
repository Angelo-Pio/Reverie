package com.sapienza.reverie.presentation.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ModeEdit
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sapienza.reverie.R
import com.sapienza.reverie.presentation.ui.components.Charm
import com.sapienza.reverie.presentation.ui.components.EditBar
import com.sapienza.reverie.presentation.ui.components.SaveButton
import com.sapienza.reverie.presentation.ui.components.TopBar
import com.sapienza.reverie.ui.theme.ReverieTheme

@Composable
fun CharmEditScreen(onCancelClick: () -> Unit = {}, onSaveClick: () -> Unit = {}) {
    Scaffold(bottomBar = { EditBar(onCancelClick = onCancelClick) }, topBar = { TopBar(title = "Build your Charm", icon = Icons.Filled.ModeEdit) }, floatingActionButton = {SaveButton(modifier = Modifier.padding(bottom = 1.dp, end = 5.dp), onClick = onSaveClick)}){
        innerPadding->

        Box(modifier = Modifier.padding(innerPadding), contentAlignment = Alignment.BottomEnd){

            Charm(
                modifier = Modifier.padding(15.dp).fillMaxSize().aspectRatio(2f/3f),
                imageUrl = R.drawable.saint_franca,
            )

        }

    }
}

@Preview
@Composable
fun CharmEditScreenPreview() {
    ReverieTheme {
        CharmEditScreen()
    }
}
