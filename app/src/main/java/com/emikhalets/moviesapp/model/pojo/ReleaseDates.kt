package com.emikhalets.moviesapp.model.pojo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ReleaseDates(
        @SerialName("results")
        val results: List<ResultReleaseDates>
)