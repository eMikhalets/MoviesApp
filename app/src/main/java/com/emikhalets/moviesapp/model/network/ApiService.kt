package com.emikhalets.moviesapp.model.network

import com.emikhalets.moviesapp.model.pojo.ConfigurationResponse
import com.emikhalets.moviesapp.model.pojo.ResponseMoviesList
import com.emikhalets.moviesapp.model.pojo.ResponsePopArtists
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("configuration")
    suspend fun configuration(): ConfigurationResponse

    @GET("movie/now_playing")
    suspend fun movieNowPlaying(
            @Query("page") page: Int = 1,
            @Query("language") language: String = "en_US"
    ): ResponseMoviesList

    @GET("movie/popular")
    suspend fun moviePopular(
            @Query("page") page: Int = 1,
            @Query("language") language: String = "en_US"
    ): ResponseMoviesList

    @GET("movie/top_rated")
    suspend fun movieTopRated(
            @Query("page") page: Int = 1,
            @Query("language") language: String = "en_US"
    ): ResponseMoviesList

    @GET("movie/upcoming")
    suspend fun movieUpcoming(
            @Query("page") page: Int = 1,
            @Query("language") language: String = "en_US"
    ): ResponseMoviesList

    @GET("person/popular")
    suspend fun personPopular(
            @Query("page") page: Int = 1,
            @Query("language") language: String = "en_US"
    ): ResponsePopArtists
}