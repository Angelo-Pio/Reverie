package com.sapienza.reverie.presentation.viewmodel

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.sapienza.reverie.domain.model.CharmModel
import com.sapienza.reverie.domain.model.CharmWithUserModel
import com.sapienza.reverie.domain.model.UserCommentModel
import com.sapienza.reverie.domain.model.UserModel
import com.sapienza.reverie.domain.repository.ApiClient
import com.sapienza.reverie.presentation.ui.components.Sticker
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.io.FileOutputStream
import java.time.LocalDateTime

class CharmViewModel() : ViewModel() {
    private val _charms = MutableStateFlow<List<CharmModel>>(emptyList())
    val charms = _charms.asStateFlow()

    private val _comments = MutableStateFlow<List<UserCommentModel>>(emptyList())
    val comments = _comments.asStateFlow()

    private val _carouselCharms = MutableStateFlow<List<CharmModel>>(emptyList())
    val carouselCharms = _carouselCharms.asStateFlow()

    private val _recentComments = MutableStateFlow<List<CharmWithUserModel>>(emptyList())
    val recentComments = _recentComments.asStateFlow() // user -  comment

    private val _newlyCollectedCharm = MutableStateFlow<CharmModel?>(null)

    val newlyCollectedCharm = _newlyCollectedCharm.asStateFlow()

    private val _charmCreator = MutableStateFlow<UserModel?>(null)
    val charmCreator = _charmCreator.asStateFlow()

    val overlayItems = mutableStateListOf<Sticker>()


    fun addSticker(sticker: Sticker) {
        overlayItems.add(sticker)
    }

    fun updateSticker(index: Int, sticker: Sticker) {
        if (index >= 0 && index < overlayItems.size) {
            overlayItems[index] = sticker
        }
    }

    fun clearSticker() {
        overlayItems.clear()
    }


    fun loadCharms(userId: Long) {
        viewModelScope.launch {
            try {
                val result = ApiClient.service.getAllCharms(userId = userId)
                _charms.value = result
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun getComments(charmId: Long) {
        viewModelScope.launch {
            try {
                val result = ApiClient.service.getCharmComments(charmId)
                _comments.value = result
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun getCarouselCharms(userId: Long) {
        viewModelScope.launch {
            try {
                val result = ApiClient.service.getCarouselCharms(userId)
                _carouselCharms.value = result
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun getRecentComments(userId: Long) {
        viewModelScope.launch {
            try {
                val result = ApiClient.service.getMostRecentComments(userId)
                _recentComments.value = result
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun postComment(userId: Long, charmId: Long, text: String) {
        viewModelScope.launch {
            try {
                val result = ApiClient.service.postComment(userId, charmId, text)
                getComments(charmId)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun collectCharm(userId: Long, charmId: Long, collected_in: String?) {
        viewModelScope.launch {
            try {
                val result = ApiClient.service.addToCollection(
                    userId = userId, charmId = charmId ,
                    collected_in = collected_in)
                _newlyCollectedCharm.value = result
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }

    }

    fun clearNewlyCollectedCharm() {
        _newlyCollectedCharm.value = null
    }

    fun getCharmCreator(charm_id: Long) {

        viewModelScope.launch {
            try {
                _charmCreator.value = ApiClient.service.madeBy(charm_id = charm_id)

            } catch (e: Exception) {
                e.printStackTrace()

            }
        }
    }

    fun clearCharmCreator() {
        _charmCreator.value = null
    }

    fun createCharm(
        context: Context,
        userId: Long,
        bitmap: Bitmap,
        description: String
    ) {
        viewModelScope.launch {
            try {

                val charmDto = CharmModel(
                    id = 0,
                    description = description,
                    pictureUrl = "",
                    created_at = LocalDateTime.now().toString(),
                    collected_in = null,
                    creator = null,
                    comments = emptyList()
                )


                val charmDtoJson = Gson().toJson(charmDto)


                val charmDtoRequestBody =
                    charmDtoJson.toRequestBody("application/json".toMediaTypeOrNull())


                val file = File(context.cacheDir, "charm_image_${System.currentTimeMillis()}.png")
                val outputStream = FileOutputStream(file)
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
                outputStream.close()
                val requestFile = file.asRequestBody("image/png".toMediaTypeOrNull())
                val body = MultipartBody.Part.createFormData("file", file.name, requestFile)



                val res = ApiClient.service.createCharm(
                    user_id = userId,
                    file = body,
                    charmDto = charmDtoRequestBody
                )

                // 6. Clean up
                file.delete()

            } catch (e: Exception) {
                // It's crucial to log this to see any errors during development
                e.printStackTrace()
            }
        }
    }

    fun clearAllData() {
        _charms.value = emptyList()
        _comments.value = emptyList()
        _carouselCharms.value = emptyList()
        _recentComments.value = emptyList()
        _newlyCollectedCharm.value = null
        _charmCreator.value = null
    }


}