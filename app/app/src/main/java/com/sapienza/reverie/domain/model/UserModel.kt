package com.sapienza.reverie.domain.model

import com.sapienza.reverie.properties.ApiProperties
import kotlinx.serialization.Serializable

@Serializable
data class UserModel(
    val id: Long,
    val username: String,
    val email: String,
    val password: String,
    val profilePicture: String,


    ){
    companion object{
        fun foo() : UserModel{
            return UserModel(
                id = 500,
                username = "foo",
                email ="foo@gmail.com",
                password = "secret",
                profilePicture = ApiProperties.API_FOO_IMAGE_PATH
            )
        }
    }
}
