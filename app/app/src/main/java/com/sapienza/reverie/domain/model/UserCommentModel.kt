package com.sapienza.reverie.domain.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class UserCommentModel(
    @SerializedName("userDto")
    val user : UserModel,

    @SerializedName("commentDto")
    val comment : CommentModel
)
