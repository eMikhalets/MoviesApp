package com.emikhalets.moviesapp.model.pojo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseCredits(
        @SerialName("cast")
        val cast: List<Cast>,
        @SerialName("crew")
        val crew: List<Crew>,
        @SerialName("id")
        val id: Int
)