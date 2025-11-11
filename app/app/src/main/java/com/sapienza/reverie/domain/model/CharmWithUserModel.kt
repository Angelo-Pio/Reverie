package com.sapienza.reverie.domain.model

import android.annotation.SuppressLint
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class CharmWithUserModel(

    @SerializedName("charm")
    val charmModel: CharmModel,

    @SerializedName("user")
    val userModel: UserModel,

    @SerializedName("comment")
    val commentModel: CommentModel
){
    companion object {

        @SuppressLint("NewApi")
        fun foo(): CharmWithUserModel {
            return CharmWithUserModel(
                charmModel = CharmModel.foo(),
                userModel = UserModel.foo(),
                commentModel = CommentModel.foo()
            )
        }
    }

}
