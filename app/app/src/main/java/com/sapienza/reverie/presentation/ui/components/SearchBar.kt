package com.sapienza.reverie.presentation.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GoogleSearchBar(
    query: String,
    onQueryChanged: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var isActive by remember { mutableStateOf(false) }
    val searchHistory = remember { mutableStateListOf("cats", "dogs", "landscapes") }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        SearchBar(
            modifier = Modifier.align(Alignment.TopCenter),
            query = query,
            onQueryChange = { newValue ->
                onQueryChanged(newValue)
            },
            onSearch = { searchQuery ->
                if (searchQuery.isNotBlank()) {
                    searchHistory.add(0, searchQuery)
                }
                isActive = false
            },
            active = isActive,
            onActiveChange = { isActive = it },
            placeholder = { Text("Search for images...") },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) }
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(searchHistory) { historyItem ->
                    Row(
                        modifier = Modifier
                            .clickable {
                                onQueryChanged(historyItem)
                            }
                    ) {
                        Icon(
                            imageVector = Icons.Default.History,
                            contentDescription = null,
                            modifier = Modifier.padding(end = 12.dp)
                        )
                        Text(text = historyItem)
                    }
                }
            }
        }
    }
}



