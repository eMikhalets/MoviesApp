package com.emikhalets.moviesapp.model.network

import com.emikhalets.moviesapp.model.pojo.ConfigurationResponse
import retrofit2.http.GET

interface ApiService {

    @GET("configuration")
    suspend fun configuration(): ConfigurationResponse
}