// app/src/main/java/com/sapienza/reverie/presentation/ui/screen/SearchImageScreen.kt
package com.sapienza.reverie.presentation.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sapienza.reverie.presentation.ui.components.GoogleSearchBar
import com.sapienza.reverie.presentation.viewmodel.SearchViewModel
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.ui.draw.clip
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.draw.shadow
import androidx.compose.foundation.background
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import com.sapienza.reverie.ui.theme.NavBarColor

@Composable
fun SearchImageScreen(
    modifier: Modifier = Modifier,
    onHomeClick: () -> Unit = {},
    onImageClick: (imageUrl: String) -> Unit = {},
    searchViewModel: SearchViewModel = viewModel()
) {
    val searchState by searchViewModel.searchState.collectAsState()

    Scaffold(
        topBar = {
            GoogleSearchBar(
                query = searchState.query,
                onQueryChanged = { searchViewModel.onSearchQueryChanged(it) }
            )
        },
        bottomBar = {
            Box(Modifier
                .clip(RoundedCornerShape(24.dp))
                .padding(6.dp)
                .shadow(elevation = 6.dp)) {
                NavigationBar(
                    modifier = modifier.background(brush = Brush.horizontalGradient(colors = NavBarColor)),
                    containerColor = Color.Transparent,
                ) {
                    NavigationBarItem(
                        icon = { Icon(Icons.Filled.Cancel, contentDescription = "Cancel") },
                        label = { Text("Cancel") },
                        selected = false,
                        onClick = onHomeClick
                    )
                }
            }
        }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            if (searchState.isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            }

            searchState.error?.let { error ->
                Text(
                    text = "Error: $error",
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(16.dp)
                )
            }

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(searchState.searchResults) { imageUrl ->
                    AsyncImage(
                        model = imageUrl,
                        contentDescription = "Search result image",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .aspectRatio(2f / 3f)
                            .clip(RoundedCornerShape(16.dp))
                            .clickable { onImageClick(imageUrl) }
                    )
                }
            }
        }
    }
}
