package com.example.gemipost.data.auth.source.remote.model.requests

@kotlinx.serialization.Serializable
data class GetUserRequest(
    val id: String = "",
)