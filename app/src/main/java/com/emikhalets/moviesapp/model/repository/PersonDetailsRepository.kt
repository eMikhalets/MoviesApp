package com.emikhalets.moviesapp.model.repository

import com.emikhalets.moviesapp.model.network.ApiResult
import com.emikhalets.moviesapp.model.network.ApiService
import com.emikhalets.moviesapp.model.pojo.ResponsePersonId
import com.emikhalets.moviesapp.utils.safeNetworkCall
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PersonDetailsRepository(
        private val api: ApiService
) {

    suspend fun requestPersonById(movieId: Int): ApiResult<ResponsePersonId> {
        return withContext(Dispatchers.IO) { safeNetworkCall { api.personById(movieId) } }
    }
}