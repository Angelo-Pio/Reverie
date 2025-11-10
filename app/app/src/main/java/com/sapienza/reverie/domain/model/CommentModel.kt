package com.sapienza.reverie.domain.model

import kotlinx.serialization.Serializable
import java.time.LocalDateTime



@Serializable
data class CommentModel (
    val id: Long,
    val text: String,
    val created_at: String
)
