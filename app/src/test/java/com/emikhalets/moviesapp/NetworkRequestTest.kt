package com.emikhalets.moviesapp

import com.emikhalets.moviesapp.model.network.ApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertNotNull
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
            prettyPrint = true
            ignoreUnknownKeys = true
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
    fun moviesNowPlayingRequestTest() {
        mockWebServer.enqueueResponse("movies_list.json", HttpURLConnection.HTTP_OK)
        runBlocking {
            val response = apiService.movieNowPlaying()
            assertNotNull(response)
        }
    }

    @Test
    fun moviePopularRequestTest() {
        mockWebServer.enqueueResponse("movies_list.json", HttpURLConnection.HTTP_OK)
        runBlocking {
            val response = apiService.moviePopular()
            assertNotNull(response)
        }
    }

    @Test
    fun movieTopRatedRequestTest() {
        mockWebServer.enqueueResponse("movies_list.json", HttpURLConnection.HTTP_OK)
        runBlocking {
            val response = apiService.movieTopRated()
            assertNotNull(response)
        }
    }

    @Test
    fun movieUpcomingRequestTest() {
        mockWebServer.enqueueResponse("movies_list.json", HttpURLConnection.HTTP_OK)
        runBlocking {
            val response = apiService.movieUpcoming()
            assertNotNull(response)
        }
    }

    @Test
    fun personPopularRequestTest() {
        mockWebServer.enqueueResponse("pop_artists.json", HttpURLConnection.HTTP_OK)
        runBlocking {
            val response = apiService.personPopular()
            assertNotNull(response)
        }
    }

    @Test
    fun movieIdRequestTest() {
        mockWebServer.enqueueResponse("movie_id.json", HttpURLConnection.HTTP_OK)
        runBlocking {
            val response = apiService.movieId(458576)
            assertNotNull(response)
        }
    }
}