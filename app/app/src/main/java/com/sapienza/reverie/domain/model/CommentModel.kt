package com.sapienza.reverie.domain.model

import android.annotation.SuppressLint
import com.sapienza.reverie.properties.ApiProperties
import kotlinx.serialization.Serializable
import java.time.LocalDateTime



@Serializable
data class CommentModel (
    val id: Long,
    val text: String,
    val created_at: String
){
    companion object {

        @SuppressLint("NewApi")
        fun foo(): CommentModel {
            return CommentModel(
                id = 500,
                text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua",
                created_at = LocalDateTime.now().toString()
            )
        }
    }

}
