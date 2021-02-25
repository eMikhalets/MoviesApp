package com.emikhalets.moviesapp.utils

import com.emikhalets.moviesapp.model.network.ApiResult

/**
 * Builds a url to request backdrop of movie with width = 1280 px
 */
fun buildBackdropUrl(path: String) = "https://image.tmdb.org/t/p/w1280/$path"

/**
 * Builds a url to request backdrop of movie with width = 780 px
 */
fun buildPosterUrl(path: String) = "https://image.tmdb.org/t/p/w780/$path"

/**
 * Builds a url to request backdrop of movie with width = 632 px
 */
fun buildProfileUrl(path: String) = "https://image.tmdb.org/t/p/w632/$path"

/**
 * Builds a url to request backdrop of movie with width = 185 px
 */
fun buildProfileMiniUrl(path: String) = "https://image.tmdb.org/t/p/w185/$path"

/**
 * Builds a url to request backdrop of movie with width = 300 px
 */
fun buildStillUrl(path: String) = "https://image.tmdb.org/t/p/w300/$path"

/**
 * Builds a url to request backdrop of movie with width = 500 px
 */
fun buildLogoUrl(path: String) = "https://image.tmdb.org/t/p/w500/$path"

/**
 * Builds a url to request backdrop of movie with width = 185 px
 */
fun buildLogoMiniUrl(path: String) = "https://image.tmdb.org/t/p/w185/$path"

//suspend fun <T> safeDatabaseCall(call: suspend () -> T): DbResult<T> =
//    try {
//        val result = call.invoke()
//        DbResult.Success(result)
//    } catch (ex: Exception) {
//        ex.printStackTrace()
//        DbResult.Error(ex.message ?: "", ex)
//    }

suspend fun <T> safeNetworkCall(call: suspend () -> T): ApiResult<T> =
    try {
        val result = call.invoke()
        ApiResult.Success(result)
    } catch (ex: Exception) {
        ex.printStackTrace()
        ApiResult.Error(ex.message ?: "", ex)
    }