package com.emikhalets.moviesapp.model.pojo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieListResult(
        @SerialName("adult")
        val adult: Boolean,
        @SerialName("id")
        val id: Int,
        @SerialName("poster_path")
        val poster_path: String,
        @SerialName("release_date")
        val release_date: String,
        @SerialName("title")
        val title: String,
        @SerialName("vote_average")
        val vote_average: Double
)