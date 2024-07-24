package com.example.gemipost.ui.post.create

import androidx.lifecycle.ViewModel
import com.example.gemipost.data.auth.repository.AuthenticationRepository
import com.example.gemipost.data.post.repository.PostRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class CreatePostViewModel(
    private val postRepository: PostRepository,
    private val authRepository: AuthenticationRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(CreatePostUIState())
    val uiState = _uiState.asStateFlow()

    fun handleEvent(createPostEvents: CreatePostEvents) {
        when (createPostEvents) {
            is CreatePostEvents.OnBodyChanged -> {
                _uiState.update { it.copy(body = createPostEvents.newBody) }
                println("CreatePostEvents.OnBodyChanged")
            }

            is CreatePostEvents.OnTitleChanged -> {
                _uiState.update { it.copy(title = createPostEvents.newTitle) }
                println("CreatePostEvents.OnTitleChanged")
            }
            is CreatePostEvents.OnAddFile -> {
                println("CreatePostEvents.OnAddFile")
            }
            is CreatePostEvents.OnAddTag -> {
                println("CreatePostEvents.OnAddTag")
            }
            CreatePostEvents.OnCreatePostClicked -> {
                println("CreatePostEvents.OnCreatePostClicked")
            }
            is CreatePostEvents.OnRemoveFile -> {
                println("CreatePostEvents.OnRemoveFile")
            }
            is CreatePostEvents.OnRemoveTag -> {
                println("CreatePostEvents.OnRemoveTag")
            }
        }
    }


}