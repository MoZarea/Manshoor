package com.example.gemipost.ui.post.feed

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gemipost.data.auth.repository.AuthenticationRepository
import com.example.gemipost.data.post.repository.PostRepository
import com.example.gemipost.data.post.source.remote.model.Post
import com.gp.socialapp.util.PostError
import kotlinx.coroutines.Dispatchers

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class FeedScreenModel(
    private val postRepo: PostRepository,
    private val authRepo: AuthenticationRepository,
) : ViewModel() {
    private val _state = MutableStateFlow(FeedUiState())
    val state = _state.asStateFlow()


    init {
        fetchPosts()
        getSignedInUser()
    }

    private fun getSignedInUser() {
        viewModelScope.launch(Dispatchers.IO) {
            authRepo.getSignedInUser().let { result ->
                result.onSuccessWithData { data ->
                        _state.update { it.copy(user = data) }
                    }.onFailure {
                        _state.update { it.copy(error = PostError.USER_NOT_FOUND) }
                }
            }
        }
    }

    private fun fetchPosts() {
        viewModelScope.launch(Dispatchers.IO) {
            postRepo.getPosts().collect { result ->
                result.onLoading {
                    println("zarea:Loading")
                }
                    .onFailure {
                        println("zarea:Failure")
                        _state.update { it.copy(error = PostError.SERVER_ERROR) }
                    }
                    .onSuccessWithData { data ->
                        println("zarea:Success")
                        _state.update {
                            it.copy(
                                posts = data
                                    .sortedByDescending { it.createdAt }
                                    .map { post ->
                                        post.copy(
                                            upvoted = if (state.value.user.id in post.upvoted) listOf(
                                                ""
                                            ) else emptyList(),
                                            downvoted = if (state.value.user.id in post.downvoted) listOf(
                                                ""
                                            ) else emptyList()
                                        )
                                    }
                            )
                        }
                    }

            }
        }
    }

    fun handleEvent(postEvent: PostEvent) {
        when (postEvent) {
            is PostEvent.OnPostDeleted -> deletePost(postEvent.post)
            is PostEvent.OnPostDownVoted -> downVotePost(postEvent.post)
            is PostEvent.OnPostReported -> TODO()
            is PostEvent.OnPostShareClicked -> _state.update { it.copy(error = PostError.SHARE_POST_IS_NOT_AVAILABLE) }
            is PostEvent.OnPostUpVoted -> upvotedPost(postEvent.post)
            is PostEvent.OnLogoutClicked -> logout()
            else -> Unit
        }
    }

    private fun logout() {
        viewModelScope.launch(Dispatchers.IO) {
            authRepo.logout().onSuccess {
                _state.update { it.copy(isLoggedIn = false) }
            }.onFailure {
                println("zarea:Logout failed")
                _state.update { it.copy(error = PostError.LOGOUT_FAILED) }
            }
        }
    }

    private fun upvotedPost(post: Post) {
        viewModelScope.launch(Dispatchers.IO) {
            postRepo.upvotePost(post, state.value.user.id).onSuccess {
                println("zarea:Post upvoted")
            }.onFailure {
                _state.update { it.copy(error = PostError.UPVOATE_FAILED) }
            }
        }
    }

    private fun downVotePost(post: Post) {
        viewModelScope.launch(Dispatchers.IO) {
            postRepo.downvotePost(post, state.value.user.id).onSuccess {
                println("zarea:Post downvoted")
            }.onFailure {
                _state.update { it.copy(error = PostError.DOWNVOTE_FAILED)}
            }
        }
    }

    private fun deletePost(post: Post) {
        viewModelScope.launch(Dispatchers.IO) {
            postRepo.deletePost(post).onSuccess {
                _state.update { it.copy(error = PostError.DELETE_POST_SUCCESS) }
            }.onFailure {
                _state.update { it.copy(error = PostError.DELETE_POST_FAILED)}
            }
        }
    }

}