package com.emikhalets.moviesapp.model.pojo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponsePersonId(
        @SerialName("adult")
        val adult: Boolean,
        @SerialName("biography")
        val biography: String?,
        @SerialName("birthday")
        val birthday: String?,
        @SerialName("deathday")
        val deathday: String?,
        @SerialName("known_for_department")
        val known_for_department: String,
        @SerialName("id")
        val id: Int,
        @SerialName("name")
        val name: String?,
        @SerialName("place_of_birth")
        val place_of_birth: String,
        @SerialName("profile_path")
        val profile_path: String?,
        @SerialName("images")
        val images: ResultImages
)