package com.example.gemipost.data.post.source.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class NotificationData(
    val topic: String = "",
    val notification: HashMap<String, String> = hashMapOf(),
    val data: HashMap<String, String> = hashMapOf()
)
