package com.emikhalets.moviesapp.model.repository

import com.emikhalets.moviesapp.model.network.ApiResult
import com.emikhalets.moviesapp.model.network.ApiService
import com.emikhalets.moviesapp.model.pojo.ResponseReviews
import com.emikhalets.moviesapp.utils.safeNetworkCall
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ReviewListRepository(
    private val api: ApiService
) {

    suspend fun requestReviews(movieId: Int): ApiResult<ResponseReviews> {
        return withContext(Dispatchers.IO) { safeNetworkCall { api.movieReviews(movieId) } }
    }
}