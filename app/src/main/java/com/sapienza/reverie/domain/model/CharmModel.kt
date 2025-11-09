package com.sapienza.reverie.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class CharmModel(
    val id : Long,
    val name : String,
    val imageUrl : Int,

)