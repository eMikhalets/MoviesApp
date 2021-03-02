package com.emikhalets.moviesapp.model.pojo

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class ResultReview(
        @SerialName("author")
        val author: String,
        @SerialName("author_details")
        val author_details: AuthorDetails,
        @SerialName("content")
        val content: String,
        @SerialName("created_at")
        val created_at: String,
        @SerialName("id")
        val id: String,
        @SerialName("updated_at")
        val updated_at: String,
        @SerialName("url")
        val url: String
) : Parcelable