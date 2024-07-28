package com.example.gemipost.data.auth.source.remote.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class UserToken(
    val token: String = ""
) : Parcelable
