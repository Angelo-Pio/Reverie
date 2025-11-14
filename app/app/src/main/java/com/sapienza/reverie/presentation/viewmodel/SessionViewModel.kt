package com.sapienza.reverie.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.sapienza.reverie.domain.model.UserModel
import com.sapienza.reverie.domain.repository.ApiClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

class SessionViewModel : ViewModel() {
    private val _user = MutableStateFlow<UserModel?>(null)
    val user: StateFlow<UserModel?> = _user

    private val _signUpError = MutableStateFlow<String?>(null)
    val signUpError: StateFlow<String?> = _signUpError.asStateFlow()


    fun loginUser(userModel: UserModel) {
        _user.value = userModel
    }

    fun logout() {
        _user.value = null
    }

    fun signUp(userModel: UserModel, file: File) {
        viewModelScope.launch {
            _signUpError.value = null
            try {
                // 1. Create RequestBody for all text fields from the UserModel
                val usernameBody =
                    userModel.username.toRequestBody("text/plain".toMediaTypeOrNull())
                val emailBody = userModel.email.toRequestBody("text/plain".toMediaTypeOrNull())
                val passwordBody =
                    userModel.password.toRequestBody("text/plain".toMediaTypeOrNull())

                // 2. Create MultipartBody.Part for the file
                val filePart = MultipartBody.Part.createFormData(
                    "file", // This name MUST match the backend parameter (`MultipartFile file`)
                    file.name,
                    file.asRequestBody("image/*".toMediaTypeOrNull())
                )

                // 3. Call the correct repository function (we named it `createUser` previously)
                ApiClient.service.signUp(
                    username = usernameBody,
                    email = emailBody,
                    password = passwordBody,
                    file = filePart
                )

                loginUser(userModel)

            } catch (e: Exception) {
                e.printStackTrace() // This is fine for debugging, it prints the stack trace

                if (e is retrofit2.HttpException) { // Use the correct import: retrofit2.HttpException
                    // --- THE FIX IS HERE ---
                    // Read the error body from the error stream, not the success body
                    val errorBody = e.response()?.errorBody()?.string()

                    if (!errorBody.isNullOrBlank()) {
                        // Now, this will contain "User with email... already exists"
                        _signUpError.value = errorBody
                    } else {
                        // Fallback message if the error body is empty
                        _signUpError.value = "An unknown error occurred (Code: ${e.code()})"
                    }
                } else {
                    // This handles other errors like no internet connection
                    _signUpError.value = "Sign up failed: ${e.message}"
                }
            }
        }
    }

}