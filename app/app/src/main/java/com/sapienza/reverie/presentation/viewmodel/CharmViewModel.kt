package com.sapienza.reverie.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sapienza.reverie.domain.model.CharmModel
import com.sapienza.reverie.domain.model.CharmWithUserModel
import com.sapienza.reverie.domain.model.UserCommentModel
import com.sapienza.reverie.domain.model.UserModel
import com.sapienza.reverie.domain.repository.ApiClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

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


    fun loadCharms(userId : Long) {
        viewModelScope.launch {
            try {
                val result = ApiClient.service.getAllCharms(userId = userId)
                _charms.value = result
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun getComments(charmId : Long) {
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

    fun postComment(userId : Long, charmId : Long, text : String){
        viewModelScope.launch {
            try {
                val result = ApiClient.service.postComment(userId, charmId, text)
                getComments(charmId)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun collectCharm(userId : Long, charmId : Long){
        viewModelScope.launch {
            try {
                val result = ApiClient.service.addToCollection(userId = userId, charmId = charmId)
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

    fun getCharmCreator(charm_id : Long) {

        viewModelScope.launch {
            try {
                _charmCreator.value =  ApiClient.service.madeBy(charm_id = charm_id)

            } catch (e: Exception) {
                e.printStackTrace()

            }
        }
    }

    fun clearCharmCreator() {
        _charmCreator.value = null
    }



}