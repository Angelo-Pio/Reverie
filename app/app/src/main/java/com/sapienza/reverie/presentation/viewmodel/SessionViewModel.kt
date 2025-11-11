package com.sapienza.reverie.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.sapienza.reverie.domain.model.UserModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

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