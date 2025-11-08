package com.sapienza.reverie.presentation.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import com.sapienza.reverie.presentation.ui.components.Charm
import com.sapienza.reverie.presentation.ui.components.NavBar
import com.sapienza.reverie.presentation.ui.components.TopBar
import com.sapienza.reverie.ui.theme.ReverieTheme


@Composable
fun CollectionScreen(modifier: Modifier = Modifier){
    Scaffold(topBar = { TopBar(title="Charm Collection" , icon = Icons.Filled.Collections)}, bottomBar = { NavBar() }) { innerPadding ->

        val fooSaints = listOf(R.drawable.saint_rick, R.drawable.saint_vale, R.drawable.saint_fede, R.drawable.saint_franca, R.drawable.saint_miriam)

        LazyVerticalGrid(
            columns = GridCells.FixedSize(110.dp),
            modifier = modifier.padding(innerPadding),
            contentPadding = PaddingValues(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            content = {items(24){


                Charm(imageUrl = fooSaints.get(1) )
            }}
        )

    }
}

@Preview
@Composable
fun CollectionScreenPreview(){
    ReverieTheme {

    CollectionScreen()
    }
}