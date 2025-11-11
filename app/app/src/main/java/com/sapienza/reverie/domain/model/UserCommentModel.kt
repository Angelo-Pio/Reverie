package com.sapienza.reverie.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class UserCommentModel(
    val user : UserModel,
    val comment : CommentModel
)
