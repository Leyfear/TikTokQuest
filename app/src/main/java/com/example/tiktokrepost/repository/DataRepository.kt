package com.example.tiktokrepost.repository

import com.example.tiktokrepost.db.DatabaseVideo
import com.example.tiktokrepost.db.VideoDao
import com.example.tiktokrepost.di.NetworkModule
import com.example.tiktokrepost.model.VideoInf
import com.example.tiktokrepost.db.VideoResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response

class DataRepository(var networkModule: NetworkModule, val databaseVideo: DatabaseVideo) {

    //Local
    val videoDao =databaseVideo.videoDao
    suspend fun upsertNote(video: VideoResponse) = videoDao.upsertVideo(video)
    suspend fun deleteNote(video:VideoResponse) = videoDao.deleteVideo(video)
    fun getNotes() = videoDao.selectVideo()
    suspend fun deleteAll() = videoDao.deleteAll()

    //Network
    suspend fun getVideoFromNetwork(
        link : String,
        apiKey : String ,
        hostKey : String) : Flow<Response<VideoInf>>{
        return flow<Response<VideoInf>>{
            val response = networkModule.sourceOfNetwork().getVideo(link,apiKey, hostKey)
            emit(response)
        }
    }


}
