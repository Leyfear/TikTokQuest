package com.example.tiktokrepost.network

sealed class ResponseModel<T>(
    val data :T? = null,
    val message : String? = null
){
    class Success<T>(data: T) : ResponseModel<T>(data)
    class Loading<T> : ResponseModel<T>()
    class Error<T>(message: String?, data: T? = null) : ResponseModel<T>(data, message)
    class Idle<T>(message: String?) : ResponseModel<T>(null, message)
}
