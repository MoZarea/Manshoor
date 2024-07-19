package com.example.gemipost.data.post.source.remote.model

import kotlinx.serialization.Serializable


sealed class PostRequest{
    @Serializable
    data class FetchRequest( val lastUpdated: Long): PostRequest()
    @Serializable
    data class UpvoteRequest(val postId: String, val userId: String): PostRequest()
    @Serializable
    data class DownvoteRequest(val postId: String, val userId: String): PostRequest()
    @Serializable
    data class DeleteRequest(val postId: String): PostRequest()
    @Serializable
    data class CreateRequest(val post: Post): PostRequest()
    @Serializable
    data class UpdateRequest(val post: Post): PostRequest()
    @Serializable
    data class ReportRequest(val postId: String, val reporterId: String): PostRequest()
}

