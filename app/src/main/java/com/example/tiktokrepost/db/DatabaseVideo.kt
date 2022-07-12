package com.example.tiktokrepost.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [VideoResponse::class], version = 1)
abstract class DatabaseVideo: RoomDatabase() {
    abstract val videoDao : VideoDao

    companion object{
        @Volatile
        var INSTANCE : DatabaseVideo? = null

        @Synchronized
        fun getDatabaseInstance(context: Context): DatabaseVideo{
            if(INSTANCE == null){
                INSTANCE = Room.databaseBuilder(
                    context,
                    DatabaseVideo::class.java,
                    "Database").fallbackToDestructiveMigration().build()
            }
            return INSTANCE!!
        }
    }
}