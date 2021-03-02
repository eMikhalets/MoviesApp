package com.emikhalets.moviesapp.model.pojo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Cast(
        @SerialName("adult")
        val adult: Boolean,
        @SerialName("cast_id")
        val cast_id: Int,
        @SerialName("known_for_department")
        val known_for_department: String,
        @SerialName("character")
        val character: String,
        @SerialName("credit_id")
        val credit_id: String,
        @SerialName("gender")
        val gender: Int?,
        @SerialName("id")
        val id: Int,
        @SerialName("name")
        val name: String,
        @SerialName("profile_path")
        val profile_path: String?
)