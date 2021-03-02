package com.emikhalets.moviesapp.model.pojo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseMovieId(
        @SerialName("adult")
        val adult: Boolean,
        @SerialName("backdrop_path")
        val backdrop_path: String?,
        @SerialName("belongs_to_collection")
        val belongs_to_collection: BelongsToCollection?,
        @SerialName("budget")
        val budget: Int,
        @SerialName("genres")
        val genres: List<Genre>,
        @SerialName("id")
        val id: Int,
        @SerialName("imdb_id")
        val imdb_id: String?,
        @SerialName("original_language")
        val original_language: String,
        @SerialName("original_title")
        val original_title: String,
        @SerialName("overview")
        val overview: String?,
        @SerialName("poster_path")
        val poster_path: String?,
        @SerialName("production_companies")
        val production_companies: List<ProductionCompany>,
        @SerialName("production_countries")
        val production_countries: List<ProductionCountry>,
        @SerialName("release_date")
        val release_date: String,
        @SerialName("revenue")
        val revenue: Int,
        @SerialName("runtime")
        val runtime: Int?,
        @SerialName("status")
        val status: String,
        @SerialName("tagline")
        val tagline: String?,
        @SerialName("title")
        val title: String,
        @SerialName("video")
        val video: Boolean,
        @SerialName("vote_average")
        val vote_average: Double,
        @SerialName("vote_count")
        val vote_count: Int
)