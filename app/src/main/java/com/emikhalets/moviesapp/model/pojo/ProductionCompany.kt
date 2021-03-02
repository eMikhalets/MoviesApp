package com.emikhalets.moviesapp.model.pojo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProductionCompany(
        @SerialName("id")
        val id: Int,
        @SerialName("logo_path")
        val logo_path: String?,
        @SerialName("name")
        val name: String,
        @SerialName("origin_country")
        val origin_country: String
)