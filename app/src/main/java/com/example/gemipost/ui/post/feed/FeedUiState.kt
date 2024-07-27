package com.example.gemipost.ui.post.feed

import com.example.gemipost.data.auth.source.remote.model.User
import com.example.gemipost.data.post.source.remote.model.Post
import com.example.gemipost.utils.AuthResults
import com.example.gemipost.utils.Error
import com.example.gemipost.utils.PostResults
import com.google.firebase.auth.AuthResult

data class FeedUiState(
    val posts: List<Post> = emptyList(),
    val user: User = User(),
    val actionResult: Error = PostResults.IDLE,
    val loginStatus: Error = AuthResults.IDLE,
    val isLoading: Boolean = false
)


