package com.example.gemipost.ui.post.create

import com.example.gemipost.data.auth.repository.AuthenticationRepository
import com.example.gemipost.data.post.repository.PostRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class CreatePostScreenModel(
    private val postRepository: PostRepository,
    private val authRepository: AuthenticationRepository,
)
{
    private val _uiState = MutableStateFlow(CreatePostUIState())
    val uiState = _uiState.asStateFlow()
}