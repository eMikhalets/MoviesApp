package com.emikhalets.moviesapp.model.pojo

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class AuthorDetails(
        @SerialName("avatar_path")
        val avatar_path: String?,
        @SerialName("rating")
        val rating: Double?
) : Parcelable