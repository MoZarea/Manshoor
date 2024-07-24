package com.example.gemipost.data.auth.source.remote.model.responses

import com.example.gemipost.data.auth.source.remote.model.User

@kotlinx.serialization.Serializable
data class UserResponse(
    val id: String = "",
    val isAdmin: Boolean = false,
    val name: String = "",
    val profilePictureURL: String = "",
    val email: String = "",
    val phoneNumber: String = "",
    val birthdate: Long = 0L,
    val bio: String = "",
    val createdAt: String = "",
) {
    fun toUser() = User(
        id = id,
        name = name,
        profilePictureURL = profilePictureURL,
        email = email,
        phoneNumber = phoneNumber,
 
    )
}