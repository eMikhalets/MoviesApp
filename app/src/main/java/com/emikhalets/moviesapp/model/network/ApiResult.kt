package com.emikhalets.moviesapp.model.network

sealed class ApiResult<out T> {
    data class Success<out T>(val result: T) : ApiResult<T>()
    data class Error(val msg: String, val exception: Exception? = null) : ApiResult<Nothing>()
    object Loading : ApiResult<Nothing>()
}