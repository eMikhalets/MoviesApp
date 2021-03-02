package com.emikhalets.moviesapp.model.pojo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BelongsToCollection(
        @SerialName("id")
        val id: Int,
        @SerialName("name")
        val name: String,
        @SerialName("poster_path")
        val poster_path: String,
        @SerialName("backdrop_path")
        val backdrop_path: String
)