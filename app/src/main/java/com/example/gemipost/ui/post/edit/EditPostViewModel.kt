package com.example.gemipost.ui.post.edit

import android.net.Uri
import androidx.core.net.toUri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gemipost.data.post.repository.PostRepository
import com.example.gemipost.data.post.source.remote.model.Tag
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
                updateBody(action.content)
            }

            is EditPostAction.OnFileAdded -> {
                updateFiles(uiState.value.postAttachments + action.file)
            }

            is EditPostAction.OnFileRemoved -> {
                updateFiles(uiState.value.postAttachments - action.file)
            }

            is EditPostAction.OnTagAdded -> {
                updateTags(uiState.value.postTags + action.tags)
            }

            is EditPostAction.OnTagRemoved -> {
                updateTags(uiState.value.postTags - action.tag)
            }

            is EditPostAction.OnTitleChanged -> {
                updateTitle(action.title)
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
                    it.onSuccess {
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
            postRepository.fetchPostById(postId).let { result ->
                result
                    .onSuccessWithData { post ->
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
                    .onFailure { error ->
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

    private fun updateTags(tags: Set<Tag>) {
        _uiState.update { it.copy(postTags = tags) }
    }

    private fun updateFiles(files: List<Uri>) {
        _uiState.update { it.copy(postAttachments = files) }
    }

    private fun updateTitle(title: String) {
        _uiState.update { it.copy(title = title) }
    }

    private fun updateBody(body: String) {
        _uiState.update { it.copy(body = body) }
    }

}