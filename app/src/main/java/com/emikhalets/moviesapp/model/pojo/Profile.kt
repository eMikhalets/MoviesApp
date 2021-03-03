package com.emikhalets.moviesapp.model.pojo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Profile(
        @SerialName("aspect_ratio")
        val aspect_ratio: Double,
        @SerialName("file_path")
        val file_path: String,
        @SerialName("height")
        val height: Int,
        @SerialName("width")
        val width: Int
)