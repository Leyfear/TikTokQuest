package com.example.tiktokrepost.network

import com.example.tiktokrepost.model.VideoInf
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface APIService {
@GET(URL.GET_VIDEO)
suspend fun getVideo(
    @Query("url") query: String? ="",
    @Header("X-RapidAPI-Key") apiKey: String,
    @Header("X-RapidAPI-Host") hostKey: String
): Response<VideoInf>
}