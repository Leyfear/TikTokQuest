package com.example.tiktokrepost.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class VideoInf(
    val OriginalWatermarkedVideo: List<String>,
    val author: List<String>,
    val avatar_thumb: List<String>,
    val cover: List<String>,
    val custom_verify: List<String>,
    val dynamic_cover: List<String>,
    val music: List<String>,
    val region: List<String>,
    val video: List<String>
):Parcelable