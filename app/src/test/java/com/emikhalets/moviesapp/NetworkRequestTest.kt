package com.emikhalets.moviesapp

import com.emikhalets.moviesapp.model.network.ApiService
import com.emikhalets.moviesapp.model.pojo.ConfigurationImagesResponse
import com.emikhalets.moviesapp.model.pojo.ConfigurationResponse
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Retrofit
import retrofit2.create
import java.net.HttpURLConnection
import java.util.concurrent.TimeUnit

@RunWith(MockitoJUnitRunner::class)
class NetworkRequestTest {

    private val mockWebServer = MockWebServer()
    private lateinit var apiService: ApiService


    @Before
    fun setUp() {
        val json = Json {
            coerceInputValues = true
            prettyPrint = true
            ignoreUnknownKeys = true
            this.isLenient = isLenient
        }
        val client = OkHttpClient.Builder()
            .connectTimeout(1, TimeUnit.SECONDS)
            .readTimeout(1, TimeUnit.SECONDS)
            .writeTimeout(1, TimeUnit.SECONDS)
            .build()
        apiService = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/").toString())
            .client(client)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
            .create()
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun configurationRequestTest() {
        mockWebServer.enqueueResponse("configuration.json", HttpURLConnection.HTTP_OK)
        runBlocking {
            val configuration = ConfigurationResponse(
                ConfigurationImagesResponse(
                    listOf("w300", "w780", "w1280", "original"),
                    listOf("w92", "w154", "w185", "w342", "w500", "w780", "original"),
                    listOf("w45", "w185", "h632", "original"),
                    "http://image.tmdb.org/t/p/",
                    "https://image.tmdb.org/t/p/"
                )
            )
            val response = apiService.configuration()
            assertEquals(configuration.toString(), response.toString())
        }
    }
}