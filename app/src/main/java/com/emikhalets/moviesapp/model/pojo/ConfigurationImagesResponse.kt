package com.emikhalets.moviesapp.model.pojo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ConfigurationImagesResponse(
    @SerialName("backdrop_sizes")
    val backdrop_sizes: List<String>,
    @SerialName("poster_sizes")
    val poster_sizes: List<String>,
    @SerialName("profile_sizes")
    val profile_sizes: List<String>,
    @SerialName("base_url")
    val base_url: String,
    @SerialName("secure_base_url")
    val secure_base_url: String
)