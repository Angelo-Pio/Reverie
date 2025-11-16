package com.sapienza.reverie.domain.model

data class SignInState(
    val isSignInSuccessful: Boolean = false,
    val signInError: String? = null
)
