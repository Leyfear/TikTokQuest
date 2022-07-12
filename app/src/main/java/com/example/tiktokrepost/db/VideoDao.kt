package com.example.tiktokrepost.db

import androidx.room.*
import kotlinx.coroutines.flow.Flow
@Dao
interface VideoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertVideo(videoResponse: VideoResponse)

    @Delete
    suspend fun deleteVideo(videoResponse: VideoResponse)

    @Query("SELECT * FROM VideoResponse ORDER BY videoId DESC")
    fun selectVideo(): Flow<List<VideoResponse>>

    @Query("DELETE FROM VideoResponse")
    suspend fun deleteAll()

}