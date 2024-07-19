package com.gp.socialapp.presentation.post.feed

sealed class FeedError {
    data class NetworkError(val message: String) : FeedError()
    object NoError : FeedError()
}