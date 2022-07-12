package com.example.tiktokrepost.db

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

import retrofit2.Response
@Entity
@Parcelize
data class VideoResponse(
    @PrimaryKey(autoGenerate = true)
    val videoId: Int = 0,
    val imageUrl: String,
    val videoUrl: String,):Parcelable