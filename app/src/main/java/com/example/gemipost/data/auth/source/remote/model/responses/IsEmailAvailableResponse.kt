package com.example.gemipost.data.auth.source.remote.model.responses

import kotlinx.serialization.Serializable

@Serializable
data class IsEmailAvailableResponse(
    val isAvailable: Boolean,
    val message: String,
)
