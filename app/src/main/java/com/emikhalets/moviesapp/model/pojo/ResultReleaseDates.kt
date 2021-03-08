package com.emikhalets.moviesapp.model.pojo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResultReleaseDates(
        @SerialName("iso_3166_1")
        val iso_3166_1: String,
        @SerialName("release_dates")
        val release_dates: List<Certification>
)