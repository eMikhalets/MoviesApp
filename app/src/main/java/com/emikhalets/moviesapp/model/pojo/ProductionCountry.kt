package com.emikhalets.moviesapp.model.pojo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProductionCountry(
        @SerialName("iso_3166_1")
        val iso_3166_1: String,
        @SerialName("name")
        val name: String
)