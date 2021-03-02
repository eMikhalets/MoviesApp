package com.emikhalets.moviesapp.model.repository

import com.emikhalets.moviesapp.model.network.ApiResult
import com.emikhalets.moviesapp.model.network.ApiService
import com.emikhalets.moviesapp.model.pojo.ResponseCredits
import com.emikhalets.moviesapp.utils.safeNetworkCall
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CastPagerRepository(
        private val api: ApiService
) {

    suspend fun requestMovieCredits(movieId: Int): ApiResult<ResponseCredits> {
        return withContext(Dispatchers.IO) { safeNetworkCall { api.movieCredits(movieId) } }
    }
}