package com.sapienza.reverie.domain.util

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sapienza.reverie.domain.model.CharmModel
import com.sapienza.reverie.domain.repository.ApiClient
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class CharmViewModel() : ViewModel() {
    private val _charms = MutableStateFlow<List<CharmModel>>(emptyList())
    val charms = _charms.asStateFlow()

    fun loadCharms(userId : Long) {
        viewModelScope.launch {
            try {
                val result = ApiClient.service.getCreatedCharms(userId = userId)
                _charms.value = result
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}