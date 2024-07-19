package com.gp.socialapp.presentation.post.edit

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.example.gemipost.data.post.repository.PostRepository
import com.example.gemipost.data.post.source.remote.model.Post
import com.example.gemipost.data.post.source.remote.model.PostAttachment
import com.example.gemipost.data.post.source.remote.model.Tag
import com.gp.socialapp.util.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class EditPostScreenModel(
    private val postRepository: PostRepository
) : ScreenModel {
    val _uiState = MutableStateFlow(EditPostUIState())
    val uiState = _uiState.asStateFlow()

    fun init(post: Post) {
        getChannelTags(post.communityID)
        _uiState.value = EditPostUIState(
            post = post,
            title = post.title,
            body = post.body,
            postTags = post.tags.toSet(),
            postAttachments = post.attachments
        )
    }

    fun onUpdatePost() {
        screenModelScope.launch {
            with(uiState.value) {
                postRepository.updatePost(
                    post.copy(
                        title = title,
                        body = body,
                        tags = postTags.toList(),
                        attachments = postAttachments
                    )
                ).let { reault ->
                    when (reault) {
                        is Result.Error -> {
                            // TODO()
                        }

                        Result.Loading -> {
                            // TODO()
                        }

                        is Result.Success -> {
                            _uiState.update {
                                it.copy(editSuccess = true)
                            }
                        }
                    }
                }
            }

        }
    }

    private fun getChannelTags(communityId: String) {
        screenModelScope.launch {
            postRepository.getAllTags(communityId).collect { tags ->
                _uiState.update {
                    it.copy(channelTags = tags.toSet())
                }
            }
        }
    }

    fun insertNewTags(tags: Set<Tag>) {
        val commTags = tags.map { it.copy(communityID = uiState.value.post.communityID) }
        screenModelScope.launch {
            commTags.toSet().forEach {
                postRepository.insertTag(it)
            }
        }
        _uiState.update { it.copy(postTags = (it.postTags + commTags)) }
    }

    fun onRemoveTags(tags: Set<Tag>) {
        val commTags = tags.map { it.copy(communityID = uiState.value.post.communityID) }
        screenModelScope.launch {
            _uiState.update { it.copy(postTags = it.postTags - commTags.toSet()) }
        }
    }

    fun onAddTags(tags: Set<Tag>) {
        if (uiState.value.postTags.contains(tags.first())) onRemoveTags(tags)
        else {
            val commTags = tags.map { it.copy(communityID = uiState.value.post.communityID) }
            screenModelScope.launch {
                _uiState.update { it.copy(postTags = it.postTags + commTags) }
            }
        }
    }

    fun onTitleChange(title: String) {
        _uiState.update {
            it.copy(title = title)
        }
    }

    fun onAddFile(postAttachment: PostAttachment) {
        screenModelScope.launch {
            _uiState.update { it.copy(postAttachments = it.postAttachments + postAttachment) }
        }
    }

    fun onRemoveFile(file: PostAttachment) {
        screenModelScope.launch(Dispatchers.Default) {
            val newFiles = uiState.value.postAttachments.toMutableList().apply {
                remove(file)
            }
            _uiState.value = uiState.value.copy(postAttachments = newFiles)
        }
    }

    fun onBodyChange(body: String) {
        _uiState.update {
            it.copy(body = body)
        }
    }

    fun dispose() {
        _uiState.value = EditPostUIState()
    }

    fun handleAction(action: EditPostAction) {
        when (action) {
            EditPostAction.OnApplyEditClicked -> {
                onUpdatePost()
            }

            is EditPostAction.OnContentChanged -> {
                onBodyChange(action.content)
            }

            is EditPostAction.OnFileAdded -> {
                onAddFile(action.file)
            }

            is EditPostAction.OnFileRemoved -> {
                onRemoveFile(action.file)
            }

            is EditPostAction.OnPreviewClicked -> {
                TODO()
            }

            is EditPostAction.OnTagAdded -> {
                onAddTags(action.tags)
            }

            is EditPostAction.OnTagRemoved -> {
                onRemoveTags(setOf(action.tag))
            }

            is EditPostAction.OnTitleChanged -> {
                onTitleChange(action.title)
            }

            else -> Unit
        }
    }


}