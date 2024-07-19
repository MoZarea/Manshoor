package com.gp.socialapp.presentation.post.create

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.gp.socialapp.data.auth.repository.AuthenticationRepository
import com.gp.socialapp.data.auth.source.remote.model.User
import com.example.gemipost.data.post.repository.PostRepository
import com.example.gemipost.data.post.source.remote.model.Post
import com.example.gemipost.data.post.source.remote.model.PostAttachment
import com.example.gemipost.data.post.source.remote.model.Tag
import com.gp.socialapp.presentation.post.feed.FeedTab
import com.gp.socialapp.util.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CreatePostScreenModel(
    private val postRepository: PostRepository,
    private val authRepository: AuthenticationRepository,
) : ScreenModel {
    private val _uiState = MutableStateFlow(CreatePostUIState())
    val uiState = _uiState.asStateFlow()
    val currentUser = MutableStateFlow(User())
    val channelTags = MutableStateFlow<List<Tag>>(emptyList())

    fun init(openedFeedTab: FeedTab, communityId: String) {
        _uiState.update { it.copy(currentTab = openedFeedTab, communityId = communityId) }
        getCurrentUser()
        getChannelTags(communityId)
    }

    private fun getChannelTags(communityId: String) {
        screenModelScope.launch {
            postRepository.getAllTags(communityId).collect { tags ->
                channelTags.update { tags }
                println(channelTags.value)
            }
        }
    }

    //
    fun insertNewTags(tags: Set<Tag>) {
        val commTags = tags.map { it.copy(communityID = uiState.value.communityId) }
        screenModelScope.launch {
            commTags.toSet().forEach {
                postRepository.insertTag(it)
            }
        }
        onAddTag(tags.first())
    }

    fun onAddTag(tag: Tag) {
        println("Adding tag")
        if (uiState.value.tags.contains(tag)) onRemoveTags(setOf(tag))
        else {
            val commTags = tag.copy(communityID = uiState.value.communityId)
            screenModelScope.launch {
                _uiState.update { it.copy(tags = it.tags + commTags) }
            }
        }


    }

    fun onRemoveTags(tags: Set<Tag>) {
        val commTags = tags.map { it.copy(communityID = uiState.value.communityId) }
        screenModelScope.launch {
            _uiState.update { it.copy(tags = it.tags - commTags.toSet()) }
        }
    }

    fun onCreatePost(title: String, body: String) {
        screenModelScope.launch {
            with(uiState.value) {
                println("Creating post" + uiState.value.communityId)
                postRepository.createPost(
                    Post(
                        communityID = communityId,
                        title = title,
                        body = body,
                        tags = tags,
                        type = uiState.value.currentTab.title,
                        authorName = currentUser.value.name,
                        authorPfp = currentUser.value.profilePictureURL,
                        authorID = currentUser.value.id,
                        attachments = files
                    )
                ).collect { it ->
                    when (it) {
                        is Result.Success -> {
                            _uiState.update { it.copy(createdState = true) }
                        }

                        is Result.Error -> {
                            println(it.message)
                        }

                        Result.Loading -> {
                            //TODO
                        }
                        else -> {}
                    }
                }
            }
        }
    }

    fun onCancel() {
//        uiState.value = uiState.value.copy(cancelPressed = true)
    }

    //
    fun resetCancelPressed() {
//        uiState.value.cancelPressed = false
    }

    private fun getCurrentUser() {
        screenModelScope.launch {
            authRepository.getSignedInUser().let {
                when (it) {
                    is Result.Success -> {
                        currentUser.value = it.data
                    }

                    is Result.Error -> {
                        println("Error: cant get user data")
                    }

                    else -> Unit
                }
            }
        }
    }

    fun onAddFile(postAttachment: PostAttachment) {
        screenModelScope.launch {
            _uiState.update { it.copy(files = it.files + postAttachment) }
        }
    }

    fun onRemoveFile(file: PostAttachment) {
        screenModelScope.launch(Dispatchers.Default) {
            val newFiles = uiState.value.files.filter { !it.file.contentEquals(file.file) }
            _uiState.value = uiState.value.copy(files = newFiles)
        }
    }

    fun resetUiState() {
        screenModelScope.launch {
            _uiState.update {
                it.copy(
                    createdState = false,
                    title = "",
                    body = "",
                    tags = emptyList(),
                    files = emptyList(),
                    cancelPressed = false,
                    communityId = "",
                    currentTab = FeedTab.entries.first()
                )
            }
        }
    }


}