package com.emikhalets.moviesapp.model.pojo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseMoviesList(
        @SerialName("page")
        val page: Int,
        @SerialName("results")
        val results: List<ResultMovieList>
)