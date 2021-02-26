package com.emikhalets.moviesapp.model.pojo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PopArtistsResult(
        @SerialName("adult")
        val adult: Boolean,
        @SerialName("gender")
        val gender: Int,
        @SerialName("id")
        val id: Int,
        @SerialName("name")
        val name: String,
        @SerialName("profile_path")
        val profile_path: String
)