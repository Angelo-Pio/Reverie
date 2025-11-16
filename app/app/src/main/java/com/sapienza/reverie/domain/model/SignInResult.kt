package com.sapienza.reverie.domain.model

data class SignInResult(
    val data: UserModel?,
    val errorMessage: String?
)
