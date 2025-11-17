package com.sapienza.reverie.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel

import com.sapienza.reverie.domain.model.UserModel
import com.sapienza.reverie.domain.repository.ApiClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

class SessionViewModel : ViewModel() {
    private val _user = MutableStateFlow<UserModel?>(null)
    val user: StateFlow<UserModel?> = _user

    private val _signUpError = MutableStateFlow<String?>(null)
    val signUpError: StateFlow<String?> = _signUpError.asStateFlow()

    fun loginUser(email:String,password: String) {
        viewModelScope.launch {
            try {
                val user = ApiClient.service.login(email, password )
                _user.value = user
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            try {

            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                _user.value = null
            }
        }
    }

    fun loginWithGoogle(idToken: String) {
        viewModelScope.launch {
            try {
                val user = ApiClient.service.loginWithGoogle(idToken)
                _user.value = user
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun signUp(userModel: UserModel, file: File) {
        viewModelScope.launch {
            _signUpError.value = null
            try {
                // 1. Create RequestBody for all text fields from the UserModel
                val usernameBody =
                    userModel.username.toRequestBody("text/plain".toMediaType())
                val emailBody = userModel.email.toRequestBody("text/plain".toMediaType())
                val passwordBody : RequestBody =
                    userModel.password!!.toRequestBody("text/plain".toMediaTypeOrNull())

                // 2. Create MultipartBody.Part for the file
                val filePart = MultipartBody.Part.createFormData(
                    "file", // This name MUST match the backend parameter (`MultipartFile file`)
                    file.name,
                    file.asRequestBody("image/*".toMediaType())
                )


                ApiClient.service.signUp(
                    username = usernameBody,
                    email = emailBody,
                    password = passwordBody,
                    file = filePart
                )

                loginUser(userModel.email,userModel.password)

            } catch (e: Exception) {
                e.printStackTrace() // This is fine for debugging, it prints the stack trace

                if (e is retrofit2.HttpException) {


                    val errorBody = e.response()?.errorBody()?.string()

                    if (!errorBody.isNullOrBlank()) {

                        _signUpError.value = errorBody
                    } else {

                        _signUpError.value = "An unknown error occurred (Code: ${e.code()})"
                    }
                } else {

                    _signUpError.value = "Sign up failed: ${e.message}"
                }
            }
        }
    }

}
