package com.gp.socialapp.presentation.post.feed

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.example.gemipost.data.auth.repository.AuthenticationRepository
import com.gp.socialapp.data.community.repository.CommunityRepository
import com.example.gemipost.data.post.repository.PostRepository
import com.example.gemipost.data.post.source.remote.model.Post
import com.example.gemipost.data.post.source.remote.model.PostAttachment
import com.example.gemipost.data.post.util.PostPopularityUtils

import com.gp.socialapp.presentation.material.utils.MimeType.Companion.getFullMimeType
import com.gp.socialapp.presentation.material.utils.MimeType.Companion.getMimeTypeFromFileName
import com.gp.socialapp.util.DispatcherIO
import com.gp.socialapp.util.Result
import io.github.aakira.napier.Napier
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class FeedScreenModel(
    private val postRepo: PostRepository,
    private val authRepo: AuthenticationRepository,
    private val communityRepository: CommunityRepository
) : ScreenModel {
    private val _state = MutableStateFlow(FeedUiState())
    val state = _state.asStateFlow()
    fun initScreen(communityId: String) {
        screenModelScope.launch(DispatcherIO) {
            authRepo.getSignedInUser().let { result ->
                when (result) {
                    is Result.Success -> {
                        _state.update {
                            it.copy(
                                currentUserID = result.data.id,
                                currentUser = result.data
                            )
                        }
                        getAllPosts()
                        getCurrentCommunity(communityId)
                    }

                    is Result.Error -> {
                        Napier.e("Error: ${result.message}")
                        _state.update { it.copy(error = FeedError.NetworkError(result.message.userMessage)) }
                    }

                    else -> Unit
                }
            }

        }

    }

    private fun getCurrentCommunity(communityId: String) {
        screenModelScope.launch(DispatcherIO) {
            communityRepository.fetchCommunity(communityId).collect { result ->
                when (result) {
                    is Result.Success -> {
                        _state.update {
                            it.copy(
                                currentCommunity = result.data
                            )
                        }
                    }

                    is Result.Error -> {
                        println("Error: ${result.message.userMessage}")
                        _state.update {
                            it.copy(
                                error = FeedError.NetworkError(result.message.userMessage)
                            )
                        }
                    }

                    else -> Unit
                }
            }
        }
    }

    private fun getAllPosts() {
        screenModelScope.launch(DispatcherIO) {
            postRepo.getPosts().collectLatest { result ->
                when (result) {
                    is Result.Success -> {
                        result.data.forEach { post ->
                            _state.update { it.copy(allTags = _state.value.allTags + post.tags) }
                        }
                        val filteredPosts = if (_state.value.selectedTags.isNotEmpty()) {
                            result.data.filter { post ->
                                post.tags.intersect(_state.value.selectedTags).isNotEmpty()
                            }
                        } else {
                            result.data
                        }
                        val sortedPosts = if (_state.value.isSortedByNewest) {
                            filteredPosts.sortedByDescending { it.createdAt }
                        } else {
                            filteredPosts.sortedByDescending {
                                PostPopularityUtils.calculateInteractionValue(
                                    it.votes,
                                    it.replyCount
                                )
                            }
                        }
                        _state.update { oldState ->
                            oldState.copy(
                                posts = sortedPosts.filter { it.communityID == oldState.currentCommunity.id },
                                isFeedLoaded = Result.Success(Unit)
                            )
                        }
                    }

                    is Result.Error -> {
                        _state.update {
                            it.copy(
                                isFeedLoaded = Result.Error(result.message),
                                error = FeedError.NetworkError(result.message.userMessage)
                            )
                        }
                    }

                    is Result.Loading -> {
                        //todo
                    }
                }
            }
        }
    }

    fun resetError() {
        screenModelScope.launch {
            _state.update { it.copy(error = FeedError.NoError) }
        }
    }

    fun upVote(post: Post) {
        screenModelScope.launch(DispatcherIO) {
            val result = postRepo.upvotePost(post, state.value.currentUserID)
            when (result) {
                is Result.Error -> {
                    _state.update { it.copy(error = FeedError.NetworkError(result.message.userMessage)) }
                }

                is Result.Success -> {
                    getAllPosts()
                }

                Result.Loading -> {
                    //todo
                }
            }

        }
    }

    fun downVote(post: Post) {
        screenModelScope.launch(DispatcherIO) {
            val result = postRepo.downvotePost(post, state.value.currentUserID)
            when (result) {
                is Result.Error -> {
                    _state.update { it.copy(error = FeedError.NetworkError(result.message.userMessage)) }
                }

                is Result.Success -> {
                    getAllPosts()
                }

                Result.Loading -> {
                    //todo
                }
            }
        }
    }

    fun reportPost(post: Post) {
        screenModelScope.launch(DispatcherIO) {
            val result = postRepo.reportPost(post.id, state.value.currentUserID)
            if (result is Result.Error) {
                _state.update { it.copy(error = FeedError.NetworkError(result.message.userMessage)) }
            }
        }
    }

    fun deletePost(post: Post) {
        screenModelScope.launch(DispatcherIO) {
            val result = postRepo.deletePost(post)
            when (result) {
                is Result.Error -> {
                    _state.update { it.copy(error = FeedError.NetworkError(result.message.userMessage)) }
                }

                is Result.Success -> {
                    getAllPosts()
                }

                Result.Loading -> {
                    //todo
                }
            }
        }
    }

    fun sortPostsByNewest() {
        screenModelScope.launch {
            _state.update { it.copy(isSortedByNewest = true) }
            getAllPosts()
        }
    }

    fun sortPostsByPopularity() {
        screenModelScope.launch {
            _state.update { it.copy(isSortedByNewest = false) }
            getAllPosts()
        }
    }

    fun updateTagFilters(newFilters: List<String>) {
        _state.update {
            it.copy(selectedTags = _state.value.allTags.filter { newFilters.contains(it.label) }
                .toSet())
        }
    }

    fun changeOpenedTab(tab: Int) {
        _state.update {
            it.copy(openedTabItem = FeedTab.entries[tab])
        }
    }

    fun logout() {
        screenModelScope.launch {
            authRepo.logout()
            _state.update { it.copy(isLoggedOut = true) }
        }
    }

    fun resetState() {
        screenModelScope.launch {
            _state.update { FeedUiState() }
        }
    }

    fun openAttachment(attachment: PostAttachment) {
        screenModelScope.launch(DispatcherIO) {
            val mimeType = getMimeTypeFromFileName(attachment.name)
            val fullMimeType = getFullMimeType(mimeType)
            postRepo.openAttachment(attachment.url, fullMimeType)
        }
    }
}