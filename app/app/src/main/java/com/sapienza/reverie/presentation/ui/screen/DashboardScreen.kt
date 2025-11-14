package com.sapienza.reverie.presentation.ui.screen

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FabPosition
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sapienza.reverie.domain.model.CharmModel
import com.sapienza.reverie.presentation.ui.components.CharmCarousel
import com.sapienza.reverie.presentation.ui.components.CommentList
import com.sapienza.reverie.presentation.ui.components.CommentType
import com.sapienza.reverie.presentation.ui.components.DashBoardNavBar
import com.sapienza.reverie.presentation.ui.components.ScanButton
import com.sapienza.reverie.presentation.ui.components.TopBar
import com.sapienza.reverie.presentation.viewmodel.CharmViewModel
import com.sapienza.reverie.presentation.viewmodel.SessionViewModel
import com.sapienza.reverie.ui.theme.ReverieTheme


@Composable
fun DashboardScreen(
    onScanClick: () -> Unit = {},
    onEditClick: () -> Unit = {},
    onLogOutClick: () -> Unit = {},
    onCollectionClick: () -> Unit = {},
    onCharmClick: (CharmModel) -> Unit = {}

) {
    val sessionViewMode: SessionViewModel = viewModel()
    val charmViewModel: CharmViewModel = viewModel()

    val user by sessionViewMode.user.collectAsState()


    // Launch loadCharms once when the screen is shown
    LaunchedEffect(user) {
        // Only proceed if the user is not null.
        user?.let { currentUser ->
            charmViewModel.getCarouselCharms(userId = currentUser.id)
            charmViewModel.getRecentComments(userId = currentUser.id)
        }
    }

    val carouselCharms by charmViewModel.carouselCharms.collectAsState()
    val recentComments by charmViewModel.recentComments.collectAsState()

    Log.e("comment list", "commentsWithCharm: $recentComments")



    Scaffold(
        bottomBar = {
            DashBoardNavBar(
                onLogOutClick,
                onCollectionClick,
                onEditClick
            )
        },
        floatingActionButton = { ScanButton(onClick = onScanClick) },
        floatingActionButtonPosition = FabPosition.End,
        topBar = {
            TopBar(
                icon = Icons.Filled.Dashboard,
                title = "Home Dashboard"
            )
        }) { innerPadding ->
        // DO NOT change innerPadding.
        // APPLY innerPadding to the root layout of your content.
        Column(
            // By applying the padding here, the entire Column and everything
            // inside it will be correctly positioned within the Scaffold's content area.
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {

            Spacer(modifier = Modifier.height(50.dp))

            if (carouselCharms.isEmpty()) {
                CircularProgressIndicator()
            } else {
                CharmCarousel(charmList = carouselCharms, onCharmClick = onCharmClick)
            }

            HorizontalDivider(
                color = Color.LightGray,
                thickness = 2.dp,
                modifier = Modifier.padding(horizontal = 15.dp, vertical = 20.dp)
            )

            CommentList(
                commentsWithCharm = recentComments,
                commentType = CommentType.IMAGE,
                comments = null,
                onCharmClick = onCharmClick
            )


        }
    }
}

@Preview
@Composable
fun DashboardScreenPreview() {
    ReverieTheme {

        DashboardScreen()
    }
}