package com.emikhalets.moviesapp.model.network

import com.emikhalets.moviesapp.BuildConfig
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class ApiKeyInterceptor : Interceptor {

    private val key = BuildConfig.ApiKey

    override fun intercept(chain: Interceptor.Chain): Response {
        val original: Request = chain.request()
        val originalUrl = original.url

        val url = originalUrl.newBuilder()
            .addQueryParameter("api_key", key)
            .build()

        val request = original.newBuilder()
            .url(url)
            .build()

        return chain.proceed(request)
    }
}