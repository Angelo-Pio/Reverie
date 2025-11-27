package com.sapienza.reverie.domain.model

import android.annotation.SuppressLint
import com.sapienza.reverie.properties.ApiProperties
import kotlinx.serialization.Serializable
import java.time.LocalDateTime

@Serializable
data class CharmModel(
    val id: Long,
    val description: String,
    val pictureUrl: String,
    val created_at: String?,
    val collected_in: String?,
    val creator: String?,
    val comments: List<CommentModel>?

){
    companion object {

        @SuppressLint("NewApi")
        fun foo(): CharmModel {
            return CharmModel(
                id = -1L, // Use -1 to indicate it's a placeholder and not a real DB entry
                description = "Default Charm",
                pictureUrl = ApiProperties.API_FOO_IMAGE_PATH,
                created_at = LocalDateTime.now().toString(),
                collected_in = "Rome",
                creator = "John Doe",
                comments = emptyList() // It's safer to use an empty list than null
            )
        }
    }
}
