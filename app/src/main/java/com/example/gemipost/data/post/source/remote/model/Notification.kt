package com.example.gemipost.data.post.source.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class Notification(
    val message: NotificationData = NotificationData(),
)
