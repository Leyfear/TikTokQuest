package com.example.tiktokrepost.di

import android.app.Application
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import com.example.tiktokrepost.db.DatabaseVideo
import com.example.tiktokrepost.db.VideoDao
import com.example.tiktokrepost.network.APIClient
import com.example.tiktokrepost.network.APIService
import com.example.tiktokrepost.repository.DataRepository
import com.example.tiktokrepost.viewmodel.ReposViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import org.koin.dsl.single

val appModule = module {
    single(named("data_repo")){ DataRepository(NetworkModule(androidContext()), get())}
    viewModel { ReposViewModel(get(named("data_repo"))) }
    single {
        Room.databaseBuilder(
            androidApplication(),
            DatabaseVideo::class.java,
            "Database"
        ).build()
    }
    single<VideoDao> {
        val database = get<DatabaseVideo>()
        database.videoDao
    }
}


    class NetworkModule(val context: Context){
            fun sourceOfNetwork(): APIService{
                return APIClient.create()
            }
    }

