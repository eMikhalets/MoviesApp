package com.emikhalets.moviesapp.model.pojo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Crew(
        @SerialName("adult")
        val adult: Boolean,
        @SerialName("gender")
        val gender: Int?,
        @SerialName("id")
        val id: Int,
        @SerialName("known_for_department")
        val known_for_department: String,
        @SerialName("name")
        val name: String,
        @SerialName("profile_path")
        val profile_path: String?,
        @SerialName("job")
        val job: String
)