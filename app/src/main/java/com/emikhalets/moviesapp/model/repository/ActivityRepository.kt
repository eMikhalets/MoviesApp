package com.emikhalets.moviesapp.model.repository

import com.emikhalets.moviesapp.model.network.ApiResult
import com.emikhalets.moviesapp.model.network.ApiService
import com.emikhalets.moviesapp.model.pojo.ConfigurationResponse
import com.emikhalets.moviesapp.utils.safeNetworkCall
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ActivityRepository(
    private val api: ApiService
) {

    suspend fun requestConfiguration(): ApiResult<ConfigurationResponse> {
        return withContext(Dispatchers.IO) { safeNetworkCall { api.configuration() } }
    }
}