package com.example.gemipost.data.auth.source.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: String = "",
    val name: String = "",
    val profilePictureURL: String = "",
    val email: String = "",
    val phoneNumber: String = "",
    val birthdate: Long = 0L,
    val bio: String = "",
    val createdAt: Long = 0L,
    val isAdmin: Boolean = false,
    val isDataComplete: Boolean = false,
)


