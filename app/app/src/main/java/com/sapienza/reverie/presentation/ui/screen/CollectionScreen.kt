package com.sapienza.reverie.presentation.ui.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Collections
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sapienza.reverie.domain.model.CharmModel
import com.sapienza.reverie.presentation.viewmodel.CharmViewModel
import com.sapienza.reverie.presentation.ui.components.Charm
import com.sapienza.reverie.presentation.ui.components.NavBar
import com.sapienza.reverie.presentation.ui.components.TopBar
import com.sapienza.reverie.presentation.viewmodel.SessionViewModel
import com.sapienza.reverie.ui.theme.ReverieTheme
import kotlinx.coroutines.delay



@Composable
fun CollectionScreen(
    modifier: Modifier = Modifier,
    onCharmClick: (charmModel: CharmModel) -> Unit = {},
    onCollectionClick: () -> Unit = {},
    onHomeClick: () -> Unit = {},
    onEditClick: () -> Unit = {}
) {

    val charmViewModel: CharmViewModel = viewModel()
    val sessionViewModel: SessionViewModel = viewModel()

    val user by sessionViewModel.user.collectAsState()


    //LaunchedEffect(user) {
        user?.let { user ->
            charmViewModel.loadCharms(user.id)
        }
    //}

    val charms by charmViewModel.charms.collectAsState()
    println(charms)

    Scaffold(
        topBar = { TopBar(title = "Charm Collection", icon = Icons.Filled.Collections) },
        bottomBar = {
            NavBar(
                onCollectionClick = onCollectionClick,
                onHomeClick = onHomeClick,
                onEditClick = onEditClick
            )
        }) { innerPadding ->


        LazyVerticalGrid(
            columns = GridCells.FixedSize(100.dp),
            modifier = modifier.padding(innerPadding),
            contentPadding = PaddingValues(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            itemsIndexed(charms) { index, charm ->

                // Each charm has its own animated visibility
                var visible by remember { mutableStateOf(false) }

                // Launch animation staggered by index
                LaunchedEffect(Unit) {
                    delay(index * 70L) // delay per item for staggered effect
                    visible = true
                }

                AnimatedVisibility(
                    visible = visible,
                    enter = slideInHorizontally(
                        initialOffsetX = { fullWidth -> -fullWidth } // from right
                    ) ,
                    exit = fadeOut()
                ) {

                    Charm(
                        charmModel = charm,
                        onClick = { onCharmClick(charm) }
                    )

                }
            }


        }
    }
}

@Preview
@Composable
fun CollectionScreenPreview() {

    ReverieTheme {

        CollectionScreen()
    }
}