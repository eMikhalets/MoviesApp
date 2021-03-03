package com.emikhalets.moviesapp.model.network

import com.emikhalets.moviesapp.model.pojo.*
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

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

    @GET("movie/{movie_id}")
    suspend fun movieId(
            @Path("movie_id") id: Int,
            @Query("append_to_response") appendRequest: String = "release_dates",
            @Query("language") language: String = "en-US"
    ): ResponseMovieId

    @GET("movie/{movie_id}/credits")
    suspend fun movieCredits(
            @Path("movie_id") id: Int,
            @Query("language") language: String = "en-US"
    ): ResponseCredits

    @GET("movie/{movie_id}/reviews")
    suspend fun movieReviews(
            @Path("movie_id") id: Int,
            @Query("page") page: Int = 1,
            @Query("language") language: String = "en-US"
    ): ResponseReviews

    @GET("movie/{movie_id}/similar")
    suspend fun movieSimilar(
            @Path("movie_id") id: Int,
            @Query("page") page: Int = 1,
            @Query("language") language: String = "en-US"
    ): ResponseSimilar

    @GET("person/popular")
    suspend fun personPopular(
            @Query("page") page: Int = 1,
            @Query("language") language: String = "en_US"
    ): ResponsePopArtists

    @GET("person/{person_id}")
    suspend fun personById(
            @Path("person_id") id: Int,
            @Query("append_to_response") append_to_response: String = "images",
            @Query("language") language: String = "en_US"
    ): ResponsePersonId
}