package com.example.gemipost.data.post.source.remote

interface NotificationRemoteDataSource {
    suspend fun sendNotificationToAuthor(
        replyId: String,
        replyContent: String,
        authorName: String,
        parentReplyId: String,
        postId: String
    )
    fun subscribeToTopic(postId: String, replyId: String)
}