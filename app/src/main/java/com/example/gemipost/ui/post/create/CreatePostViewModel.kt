package com.example.gemipost.ui.post.create

import android.net.Uri
import androidx.compose.ui.util.trace
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gemipost.data.auth.repository.AuthenticationRepository
import com.example.gemipost.data.post.repository.PostRepository
import com.example.gemipost.data.post.source.remote.model.Post
import com.example.gemipost.data.post.source.remote.model.Tag
import com.example.gemipost.utils.Error
import com.example.gemipost.utils.LocalDateTimeUtil.now
import com.example.gemipost.utils.PostResults
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant

class CreatePostViewModel(
    private val postRepository: PostRepository,
    private val authRepository: AuthenticationRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(CreatePostUIState())
    val uiState = _uiState.asStateFlow()

    init {
        getCurrentUser()
    }

    private fun getCurrentUser() {
        viewModelScope.launch(Dispatchers.IO) {
            authRepository.getSignedInUser().let { result ->
                result.onSuccessWithData { data ->
                    _uiState.update { it.copy(user = data) }
                }
            }
        }
    }

    fun handleEvent(createPostEvents: CreatePostEvents) =
        when (createPostEvents) {
            is CreatePostEvents.OnBodyChanged -> {
                updateBody(createPostEvents.newBody)
            }

            is CreatePostEvents.OnTitleChanged -> {
                updateTitle(createPostEvents.newTitle)
            }

            is CreatePostEvents.OnAddFile -> {
                updateFiles(uiState.value.files + createPostEvents.uri)
            }

            is CreatePostEvents.OnAddTag -> {
                updateTags(uiState.value.tags + createPostEvents.tag)
            }

            CreatePostEvents.OnCreatePostClicked -> {
                createPost()
            }

            is CreatePostEvents.OnRemoveFile -> {
                updateFiles(uiState.value.files - createPostEvents.uri)
            }

            is CreatePostEvents.OnRemoveTag -> {
                updateTags(uiState.value.tags - createPostEvents.tag)
            }
        }

    private fun createPost() {
        with(uiState.value) {
            viewModelScope.launch(Dispatchers.IO) {
                postRepository.createPost(
                    Post(
                        authorName = user?.name ?: "",
                        authorPfp = user?.profilePictureURL ?: "",
                        title = title,
                        body = body,
                        tags = tags.toList(),
                        attachments = files.map { it.toString() },
                        createdAt = LocalDateTime.now().toInstant(TimeZone.UTC)
                            .toEpochMilliseconds()

                    )
                ).collect { result ->
                    result
                        .onSuccessWithData {message->
                            updateUserMessage(message)
                        }
                        .onFailure { message ->
                            updateUserMessage(message)
                        }
                        .onLoading {
                            enableLoading()
                        }
                }
            }
        }
    }

    private fun updateUserMessage(message: Error) {
        println("updateUserMessage: $message")
        _uiState.update { it.copy(actionResult = message, isLoading = false) }
    }

    private fun enableLoading() {
        _uiState.update { it.copy(isLoading = true) }
    }

    private fun updateTags(tags: List<Tag>) {
        _uiState.update { it.copy(tags = tags) }
    }

    private fun updateFiles(files: List<Uri>) {
        _uiState.update { it.copy(files = files) }
    }

    private fun updateTitle(title: String) {
        _uiState.update { it.copy(title = title) }
    }

    private fun updateBody(body: String) {
        _uiState.update { it.copy(body = body) }
    }

    fun resetState() {
        _uiState.update { it.copy(actionResult = PostResults.IDLE)}
    }
}