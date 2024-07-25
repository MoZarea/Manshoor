package com.example.gemipost.ui.post.edit

import androidx.core.net.toUri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gemipost.data.post.repository.PostRepository
import com.example.gemipost.data.post.source.remote.model.Post
import com.example.gemipost.utils.LocalDateTimeUtil.now
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant

class EditPostViewModel(
    private val postRepository: PostRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(EditPostUIState())
    val uiState = _uiState.asStateFlow()
    fun handleActions(action: EditPostAction) {
        when (action) {
            EditPostAction.OnApplyEditClicked -> {
                applyChanges()
            }

            is EditPostAction.OnContentChanged -> {
                _uiState.update { it.copy(body = action.content) }
            }
            is EditPostAction.OnFileAdded -> {
                _uiState.update { it.copy(postAttachments = it.postAttachments + action.file) }
            }
            is EditPostAction.OnFileRemoved -> {
                _uiState.update { it.copy(postAttachments = it.postAttachments - action.file) }
            }
            is EditPostAction.OnTagAdded -> {
                _uiState.update { it.copy(postTags = it.postTags + action.tags) }
            }
            is EditPostAction.OnTagRemoved -> {
                _uiState.update { it.copy(postTags = it.postTags - action.tag) }
            }
            is EditPostAction.OnTitleChanged -> {
                _uiState.update { it.copy(title = action.title) }
            }
        }
    }

    private fun applyChanges() {
        with(uiState.value) {
            viewModelScope.launch(Dispatchers.IO) {
                postRepository.updatePost(
                    _uiState.value.post.copy(
                        title = title,
                        body = body,
                        tags = postTags.toList(),
                        attachments = postAttachments.map { it.toString() },
                        createdAt = LocalDateTime.now().toInstant(TimeZone.UTC)
                            .toEpochMilliseconds()
                    )
                ).let {
                    it
                        .onSuccess {
                            updateUserMessage("Post Updated Successfully")
                        }.onFailure { error ->
                            updateUserMessage(error.userMessage)
                        }.onLoading {
                            updateLoading(true)
                        }
                }
            }
        }
    }

    fun init(postId: String) {
        if (uiState.value.post.id.isNotBlank()) return
        viewModelScope.launch(Dispatchers.IO) {
            postRepository.fetchPostById(postId).let {result->
                result
                    .onSuccessWithData {post->
                        _uiState.update {
                            it.copy(
                                post = post,
                                title = post.title,
                                body = post.body,
                                postTags = post.tags.toMutableSet(),
                                postAttachments = post.attachments.map { it.toUri() }
                            )
                        }
                    }
                    .onFailure { error->
                        updateUserMessage(error.userMessage)
                    }
                    .onLoading {
                        updateLoading(true)
                    }
            }
        }
    }
    private fun updateUserMessage(message: String) {
        println("updateUserMessage: $message")
        _uiState.update { it.copy(userMessage = message) }
        updateLoading(false)
    }
    private fun updateLoading(isLoading: Boolean) {
        println("updateLoading: $isLoading")
        _uiState.update { it.copy(isLoading = isLoading) }
    }

}