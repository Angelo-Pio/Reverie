package com.sapienza.reverie.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sapienza.reverie.domain.model.UserModel
import com.sapienza.reverie.domain.repository.ApiClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SessionViewModel : ViewModel() {
    private val _user = MutableStateFlow<UserModel?>(null)
    val user: StateFlow<UserModel?> = _user

    fun loginUser(userModel: UserModel) {
        _user.value = userModel
    }

    fun logout() {
        _user.value = null
    }


}