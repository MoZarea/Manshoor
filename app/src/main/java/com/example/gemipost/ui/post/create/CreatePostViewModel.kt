package com.example.gemipost.ui.post.create

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gemipost.data.auth.repository.AuthenticationRepository
import com.example.gemipost.data.post.repository.PostRepository
import com.example.gemipost.data.post.source.remote.model.Post
import com.example.gemipost.utils.LocalDateTimeUtil.now
import com.example.gemipost.utils.Status
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
                result.onSuccessWithData {data->
                    _uiState.update { it.copy(user =data ) }
                }
            }
        }
    }

    fun handleEvent(createPostEvents: CreatePostEvents) =
        when (createPostEvents) {
            is CreatePostEvents.OnBodyChanged -> {
                _uiState.update { it.copy(body = createPostEvents.newBody) }
            }

            is CreatePostEvents.OnTitleChanged -> {
                _uiState.update { it.copy(title = createPostEvents.newTitle) }
            }

            is CreatePostEvents.OnAddFile -> {
                _uiState.update { it.copy(files = it.files + createPostEvents.uri) }
            }

            is CreatePostEvents.OnAddTag -> {
                _uiState.update { it.copy(tags = it.tags + createPostEvents.tag) }
            }

            CreatePostEvents.OnCreatePostClicked -> {
                createPost()
            }

            is CreatePostEvents.OnRemoveFile -> {
                _uiState.update { it.copy(files = it.files - createPostEvents.uri) }
            }

            is CreatePostEvents.OnRemoveTag -> {
                _uiState.update {
                    it.copy(tags = it.tags - createPostEvents.tag)
                }
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
                        .onSuccess {
                            _uiState.update { it.copy(status = Status.SUCCESS) }
                        }
                        .onFailure { message ->
                            _uiState.update { it.copy(status = Status.ERROR(message.userMessage)) }
                        }
                        .onLoading {
                            _uiState.update { it.copy(status = Status.LOADING) }
                        }
                }
            }
        }
    }
}