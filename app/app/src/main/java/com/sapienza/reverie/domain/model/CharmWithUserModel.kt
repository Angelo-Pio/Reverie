package com.sapienza.reverie.domain.model

import android.annotation.SuppressLint
import kotlinx.serialization.Serializable

@Serializable
data class CharmWithUserModel(
    val charmModel: CharmModel,
    val userModel: UserModel,
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
