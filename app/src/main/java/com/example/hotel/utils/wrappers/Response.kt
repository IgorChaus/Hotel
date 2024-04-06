package com.example.hotel.utils.wrappers

import java.lang.Exception

sealed class Response<out T> {
    data class Success<T>(val data: T) : Response<T>()
    data class Error(val exception: Exception) : Response<Nothing>()
}