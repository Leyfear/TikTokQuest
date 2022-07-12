package com.example.tiktokrepost.viewmodel

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tiktokrepost.model.VideoInf
import com.example.tiktokrepost.db.VideoResponse
import com.example.tiktokrepost.network.ResponseModel
import com.example.tiktokrepost.repository.DataRepository
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Response

class ReposViewModel(var dataRepository: DataRepository): ViewModel() {

    val uiUpdates =
        MutableStateFlow<ResponseModel<Response<VideoInf>>>(ResponseModel.Idle("Idle State"))

    val videos = dataRepository.getNotes()
    fun upsertVideo(videoResponse: VideoResponse) = viewModelScope.launch {
        dataRepository.upsertNote(videoResponse)
    }
    fun deleteVideo(videoResponse: VideoResponse) = viewModelScope.launch {
        dataRepository.deleteNote(videoResponse)
    }
    fun deleteAll() = viewModelScope.launch {
        dataRepository.deleteAll()
    }


    suspend fun getVideo(
        link: String,
        apiKey: String,
        hostKey: String
    ) {
        uiUpdates.emit(ResponseModel.Loading())
        dataRepository.getVideoFromNetwork(link, apiKey, hostKey).collect {
            viewModelScope.launch {
                if (it.isSuccessful) {
                    uiUpdates.emit(ResponseModel.Success(it))
                } else {
                    uiUpdates.emit(ResponseModel.Error(it.message()))
                }
            }
        }
    }








}