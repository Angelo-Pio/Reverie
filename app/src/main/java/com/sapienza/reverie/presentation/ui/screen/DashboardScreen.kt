package com.sapienza.reverie.presentation.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FabPosition
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sapienza.reverie.R
import com.sapienza.reverie.presentation.ui.components.Charm
import com.sapienza.reverie.presentation.ui.components.CharmCarousel
import com.sapienza.reverie.presentation.ui.components.CharmComment
import com.sapienza.reverie.presentation.ui.components.NavBar
import com.sapienza.reverie.presentation.ui.components.ScanButton
import com.sapienza.reverie.presentation.ui.components.TopBar


@Composable
fun DashboardScreen() {
    Scaffold(
        bottomBar = { NavBar() },
        floatingActionButton = { ScanButton(onClick = {}) },
        floatingActionButtonPosition = FabPosition.End,
        topBar = { TopBar() }
    ) { innerPadding ->
        // DO NOT change innerPadding.
        // APPLY innerPadding to the root layout of your content.
        Column(
            // By applying the padding here, the entire Column and everything
            // inside it will be correctly positioned within the Scaffold's content area.
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding) // Apply background to the padded area if desired
        ) {

            Spacer(modifier = Modifier.height(50.dp))
            CharmCarousel(Modifier.padding(horizontal = 15.dp) )
            HorizontalDivider(color = Color.LightGray, thickness = 2.dp, modifier = Modifier.padding(horizontal = 15.dp, vertical = 20.dp))
            CharmComment(R.drawable.saint_rick, "This is a comment",)


        }
    }
}

@Preview
@Composable
fun DashboardScreenPreview() {
    DashboardScreen()
}