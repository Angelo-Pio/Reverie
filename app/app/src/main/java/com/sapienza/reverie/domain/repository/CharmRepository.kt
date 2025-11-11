package com.sapienza.reverie.domain.repository

import com.sapienza.reverie.domain.model.CharmModel
import com.sapienza.reverie.domain.model.CharmWithUserModel
import com.sapienza.reverie.domain.model.CommentModel
import com.sapienza.reverie.domain.model.UserCommentModel
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CharmRepository {
    @GET("charm/created")
    suspend fun getCreatedCharms(@Query("user_id")userId : Long): List<CharmModel>

    @GET("charm/{id}")
    suspend fun getCharmById(@Path("id") id: Long): CharmModel

    @GET("charm/random")
    suspend fun getCarouselCharms(@Query("user_id") id: Long): List<CharmModel>

    @GET("charm/comment")
    suspend  fun getCharmComments(@Query("charm_id") id: Long): List<UserCommentModel>

    @GET("/charm/comment/recent")
    suspend fun getMostRecentComments(@Query("user_id" )userId: Long): List<CharmWithUserModel>


}