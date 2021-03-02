package com.emikhalets.moviesapp.model.pojo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Crew(
        @SerialName("adult")
        val adult: Boolean,
        @SerialName("credit_id")
        val credit_id: String,
        @SerialName("department")
        val department: String,
        @SerialName("gender")
        val gender: Int?,
        @SerialName("id")
        val id: Int,
        @SerialName("job")
        val job: String,
        @SerialName("name")
        val name: String,
        @SerialName("profile_path")
        val profile_path: String?
)