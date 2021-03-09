package com.emikhalets.moviesapp.model.network

import com.emikhalets.moviesapp.model.pojo.*
import com.emikhalets.moviesapp.utils.Lang
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("movie/now_playing")
    suspend fun movieNowPlaying(
            @Query("page") page: Int = 1,
            @Query("language") language: String = Lang.lang
    ): ResponseMoviesList

    @GET("movie/popular")
    suspend fun moviePopular(
            @Query("page") page: Int = 1,
            @Query("language") language: String = Lang.lang
    ): ResponseMoviesList

    @GET("movie/top_rated")
    suspend fun movieTopRated(
            @Query("page") page: Int = 1,
            @Query("language") language: String = Lang.lang
    ): ResponseMoviesList

    @GET("movie/upcoming")
    suspend fun movieUpcoming(
            @Query("page") page: Int = 1,
            @Query("language") language: String = Lang.lang
    ): ResponseMoviesList

    @GET("movie/{movie_id}")
    suspend fun movieId(
            @Path("movie_id") id: Int,
            @Query("append_to_response") appendRequest: String = "release_dates",
            @Query("language") language: String = Lang.lang
    ): ResponseMovieId

    @GET("movie/{movie_id}/credits")
    suspend fun movieCredits(
            @Path("movie_id") id: Int,
            @Query("language") language: String = Lang.lang
    ): ResponseCredits

    @GET("movie/{movie_id}/reviews")
    suspend fun movieReviews(
            @Path("movie_id") id: Int,
            @Query("page") page: Int = 1,
            @Query("language") language: String = Lang.lang
    ): ResponseReviews

    @GET("movie/{movie_id}/similar")
    suspend fun movieSimilar(
            @Path("movie_id") id: Int,
            @Query("page") page: Int = 1,
            @Query("language") language: String = Lang.lang
    ): ResponseSimilar

    @GET("person/popular")
    suspend fun personPopular(
            @Query("page") page: Int = 1,
            @Query("language") language: String = Lang.lang
    ): ResponsePopArtists

    @GET("person/{person_id}")
    suspend fun personById(
            @Path("person_id") id: Int,
            @Query("append_to_response") append_to_response: String = "images",
            @Query("language") language: String = Lang.lang
    ): ResponsePersonId

    @GET("search/movie")
    suspend fun searchMovies(
            @Query("query") query: String,
            @Query("page") page: Int = 1,
            @Query("include_adult") include_adult: Boolean = false,
            @Query("language") language: String = Lang.lang
    ): ResponseMoviesList
}