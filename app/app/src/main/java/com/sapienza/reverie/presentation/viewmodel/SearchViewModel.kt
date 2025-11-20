package com.sapienza.reverie.presentation.viewmodel

// app/src/main/java/com/sapienza/reverie/presentation/viewmodel/SearchViewModel.kt

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sapienza.reverie.domain.util.GoogleSearchRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class SearchState(
    val query: String = "",
    val searchResults: List<String> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

class SearchViewModel : ViewModel() {

    private val googleSearchRepository = GoogleSearchRepository()

    private val _searchState = MutableStateFlow(SearchState())
    val searchState: StateFlow<SearchState> = _searchState.asStateFlow()

    fun onSearchQueryChanged(query: String) {
        _searchState.value = _searchState.value.copy(query = query)
        if (query.length > 2) {
            searchImages(query)
        }
    }

    private fun searchImages(query: String) {
        viewModelScope.launch {
            _searchState.value = _searchState.value.copy(isLoading = true, error = null)
            try {
                val results = googleSearchRepository.searchImages(query)
                _searchState.value = _searchState.value.copy(
                    searchResults = results.mapNotNull { it.image?.thumbnail },
                    isLoading = false
                )
            } catch (e: Exception) {
                _searchState.value = _searchState.value.copy(isLoading = false, error = e.message)
            }
        }
    }
}