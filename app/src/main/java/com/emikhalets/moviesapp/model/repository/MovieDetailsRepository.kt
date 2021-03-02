package com.emikhalets.moviesapp.model.repository

import com.emikhalets.moviesapp.model.network.ApiResult
import com.emikhalets.moviesapp.model.network.ApiService
import com.emikhalets.moviesapp.model.pojo.ResponseCredits
import com.emikhalets.moviesapp.model.pojo.ResponseMovieId
import com.emikhalets.moviesapp.model.pojo.ResponseReviews
import com.emikhalets.moviesapp.model.pojo.ResponseSimilar
import com.emikhalets.moviesapp.utils.safeNetworkCall
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MovieDetailsRepository(
        private val api: ApiService
) {

    suspend fun requestMovieById(movieId: Int): ApiResult<ResponseMovieId> {
        return withContext(Dispatchers.IO) { safeNetworkCall { api.movieId(movieId) } }
    }

    suspend fun requestCredits(movieId: Int): ApiResult<ResponseCredits> {
        return withContext(Dispatchers.IO) { safeNetworkCall { api.movieCredits(movieId) } }
    }

    suspend fun requestReviews(movieId: Int): ApiResult<ResponseReviews> {
        return withContext(Dispatchers.IO) { safeNetworkCall { api.movieReviews(movieId) } }
    }

    suspend fun requestMoviesSimilar(movieId: Int): ApiResult<ResponseSimilar> {
        return withContext(Dispatchers.IO) { safeNetworkCall { api.movieSimilar(movieId) } }
    }
}