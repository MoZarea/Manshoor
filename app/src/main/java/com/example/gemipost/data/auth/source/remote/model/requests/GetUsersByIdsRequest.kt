package com.example.gemipost.data.auth.source.remote.model.requests

import kotlinx.serialization.Serializable

@Serializable
data class GetUsersByIdsRequest(
    val ids: List<String> = emptyList(),
)
