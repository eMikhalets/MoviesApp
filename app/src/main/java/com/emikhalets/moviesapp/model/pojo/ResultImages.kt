package com.emikhalets.moviesapp.model.pojo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResultImages(
        @SerialName("profiles")
        val profiles: List<Profile>
)