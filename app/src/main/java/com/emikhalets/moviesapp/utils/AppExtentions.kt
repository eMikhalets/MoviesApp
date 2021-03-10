package com.emikhalets.moviesapp.utils

import android.content.res.Resources
import android.util.TypedValue
import com.emikhalets.moviesapp.model.network.ApiResult
import com.emikhalets.moviesapp.model.pojo.Profile

// ================= Allowed backdrop sizes: w300, w780, w1280

/**
 * Builds a url to request backdrop of movie with width = 780 px
 */
fun buildBackdrop780px(path: String) = "https://image.tmdb.org/t/p/w780/$path"

// ================= Allowed poster sizes: w92, w154, w185, w342, w500, w780

/**
 * Builds a url to request poster of movie with width = 185 px
 */
fun buildPoster185px(path: String) = "https://image.tmdb.org/t/p/w185/$path"

/**
 * Builds a url to request poster of movie with width = 780 px
 */
fun buildPoster780px(path: String) = "https://image.tmdb.org/t/p/w780/$path"

// ================= Allowed profile sizes: w45, w185, w632

/**
 * Builds a url to request profile of movie with width = 185 px
 */
fun buildProfile185px(path: String) = "https://image.tmdb.org/t/p/w185/$path"

/**
 * Builds a url to request profile of movie with width = 185 px
 */
suspend fun <T> safeNetworkCall(call: suspend () -> T): ApiResult<T> =
        try {
            val result = call.invoke()
            ApiResult.Success(result)
        } catch (ex: Exception) {
            ex.printStackTrace()
            ApiResult.Error(ex.message ?: "", ex)
        }

fun formatImagesList(path: String): ArrayList<String> {
    return arrayListOf(path)
}

fun formatImagesList(images: List<Profile>): ArrayList<String> {
    val list = ArrayList<String>()
    val paths = images.map { it.file_path }
    paths.forEach { list.add(it) }
    return list
}

fun imageCornerValue(resources: Resources): Float {
    return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            2f,
            resources.displayMetrics
    )
}