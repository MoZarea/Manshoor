package com.example.gemipost.data.auth.source.remote.model.responses

import com.example.gemipost.data.auth.source.remote.model.User
import kotlinx.serialization.Serializable

@Serializable
data class AuthResponse(
    val errorMessage: String,
    val user: User
)