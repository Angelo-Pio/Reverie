package com.sapienza.reverie.presentation.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Collections
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sapienza.reverie.R
import com.sapienza.reverie.domain.model.CharmModel
import com.sapienza.reverie.presentation.ui.components.Charm
import com.sapienza.reverie.presentation.ui.components.NavBar
import com.sapienza.reverie.presentation.ui.components.TopBar
import com.sapienza.reverie.ui.theme.ReverieTheme


@Composable
fun CollectionScreen(
    modifier: Modifier = Modifier,
    onCharmClick: (charmModel: CharmModel) -> Unit = {},
    onCollectionClick: () -> Unit = {},
    onHomeClick: () -> Unit = {},
    onEditClick: () -> Unit = {}
) {
    Scaffold(
        topBar = { TopBar(title = "Charm Collection", icon = Icons.Filled.Collections) },
        bottomBar = { NavBar(onCollectionClick= onCollectionClick, onHomeClick = onHomeClick, onEditClick = onEditClick) }) { innerPadding ->

        val charmsFromDb = listOf(
            CharmModel(1, "Saint Rick", R.drawable.saint_rick),
            CharmModel(2, "Saint Vale",  R.drawable.saint_vale),
            CharmModel(3, "Saint Fede",  R.drawable.saint_fede),
            CharmModel(4, "Saint Franca",  R.drawable.saint_franca),
            CharmModel(5, "Saint Miriam",  R.drawable.saint_miriam)
        )

        LazyVerticalGrid(
            columns = GridCells.FixedSize(110.dp),
            modifier = modifier.padding(innerPadding),
            contentPadding = PaddingValues(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            content = {
                items(charmsFromDb) { charm ->


                    Charm(
                        imageUrl = charm.imageUrl, onClick = { onCharmClick(charm) }
                    )
                }
            })

    }
}

@Preview
@Composable
fun CollectionScreenPreview() {
    ReverieTheme {

        CollectionScreen()
    }
}