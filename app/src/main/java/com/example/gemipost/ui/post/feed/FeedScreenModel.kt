package com.example.gemipost.ui.post.feed

import com.example.gemipost.data.auth.repository.AuthenticationRepository
import com.example.gemipost.data.post.repository.PostRepository

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class FeedScreenModel(
    private val postRepo: PostRepository,
    private val authRepo: AuthenticationRepository,
)  {
    private val _state = MutableStateFlow(FeedUiState())
    val state = _state.asStateFlow()

}