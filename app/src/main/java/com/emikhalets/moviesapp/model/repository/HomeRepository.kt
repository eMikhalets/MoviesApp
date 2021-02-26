package com.emikhalets.moviesapp.model.repository

import com.emikhalets.moviesapp.model.network.ApiResult
import com.emikhalets.moviesapp.model.network.ApiService
import com.emikhalets.moviesapp.model.pojo.ResponseMoviesList
import com.emikhalets.moviesapp.model.pojo.ResponsePopArtists
import com.emikhalets.moviesapp.utils.safeNetworkCall
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class HomeRepository(
        private val api: ApiService
) {

    suspend fun requestPersonPopular(): ApiResult<ResponsePopArtists> {
        return withContext(Dispatchers.IO) { safeNetworkCall { api.personPopular() } }
    }

    suspend fun requestMoviesPopular(): ApiResult<ResponseMoviesList> {
        return withContext(Dispatchers.IO) { safeNetworkCall { api.moviePopular() } }
    }

    suspend fun requestMoviesNowPlaying(): ApiResult<ResponseMoviesList> {
        return withContext(Dispatchers.IO) { safeNetworkCall { api.movieNowPlaying() } }
    }

    suspend fun requestMoviesTopRated(): ApiResult<ResponseMoviesList> {
        return withContext(Dispatchers.IO) { safeNetworkCall { api.movieTopRated() } }
    }

    suspend fun requestMoviesUpcoming(): ApiResult<ResponseMoviesList> {
        return withContext(Dispatchers.IO) { safeNetworkCall { api.movieUpcoming() } }
    }
}