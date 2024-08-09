package com.example.gemipost.ui.post.feed

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gemipost.R
import com.example.gemipost.data.auth.repository.AuthenticationRepository
import com.example.gemipost.data.auth.source.remote.model.User
import com.example.gemipost.data.post.repository.PostRepository
import com.example.gemipost.data.post.source.remote.model.Post
import com.example.gemipost.utils.AuthResults
import com.example.gemipost.utils.Error
import com.example.gemipost.utils.PostResults
import com.example.gemipost.utils.urlToBitmap
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.gp.socialapp.util.Result
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
                println("getSignedInUser: $result")
                result.onSuccessWithData { data ->
                    updateCurrentUser(data)
                    updateLoginResults(AuthResults.LOGIN_SUCCESS)
                }.onFailure {
                    updateLoginResults(it)
                }
            }
            authRepo.getUserToken().let { result ->
                if (result is Result.Success) {
                    authRepo.registerUserToken(result.data).collect{
                        if (it is Result.Success) {
                            Log.d("seerde", "User token registered")
                        } else {
                            Log.d("seerde", "Error registering user token 2")
                        }
                    }
                } else {
                    Log.d("seerde", "Error getting user token")
                }
            }
        }
    }

    private fun updateLoginResults(result: Error) {
        println("updateLoginResults: $result")
        _state.update { it.copy(loginStatus = result) }
    }

    private fun fetchPosts() {
        viewModelScope.launch(Dispatchers.IO) {
            postRepo.getPosts().collect { result ->
                println("fetchPosts: $result")
                result.onLoading {
                    updateLoading(true)
                }.onFailure {
                    updateUserMessage(it)
                }.onSuccessWithData { data ->
                    updatePosts(data
                        .sortedByDescending { it.createdAt }
                    )
                }
            }
        }
    }

    fun handleEvent(postEvent: PostEvent) {
        when (postEvent) {
            is PostEvent.OnPostDeleted -> deletePost(postEvent.post)
            is PostEvent.OnPostDownVoted -> downVotePost(postEvent.post)
            is PostEvent.OnPostReported -> reportPost(postEvent.post, postEvent.context)
            is PostEvent.OnPostUpVoted -> upvotedPost(postEvent.post)
            is PostEvent.OnPostClicked -> resetState()
            is PostEvent.OnCreatePostClicked -> resetState()
            is PostEvent.OnSearchClicked -> resetState()
            is PostEvent.OnPostEdited -> resetState()
            is PostEvent.OnLogoutClicked -> logout()
            else -> Unit
        }
    }

    private fun resetState() {
        _state.update { it.copy(actionResult = PostResults.IDLE) }
    }

    private fun logout() {
        viewModelScope.launch(Dispatchers.IO) {
            authRepo.logout().onSuccessWithData {
                updateUserMessage(it)
            }.onFailure {
                updateUserMessage(it)
            }
        }
    }

    private fun reportPost(post: Post, context: Context) {
        if (state.value.loginStatus != AuthResults.LOGIN_SUCCESS) return
        viewModelScope.launch(Dispatchers.IO) {
            val images =
                urlToBitmap(
                    scope = this@launch,
                    imageURLs = post.attachments,
                    context = context,
                )
            postRepo.reportPost(post.id, post.title, post.body, images).onSuccessWithData {
                updateUserMessage(it)
            }.onFailure {
                updateUserMessage(it)
            }
        }
    }

    private fun upvotedPost(post: Post) {
        if (state.value.loginStatus != AuthResults.LOGIN_SUCCESS) return
        viewModelScope.launch(Dispatchers.IO) {
            postRepo.upvotePost(post, state.value.user.id).onFailure {
                updateUserMessage(it)
            }
        }
    }

    private fun downVotePost(post: Post) {
        if(state.value.loginStatus != AuthResults.LOGIN_SUCCESS) return
        viewModelScope.launch(Dispatchers.IO) {
            postRepo.downvotePost(post, state.value.user.id).onFailure {
                updateUserMessage(it)
            }
        }
    }

    private fun deletePost(post: Post) {
        viewModelScope.launch(Dispatchers.IO) {
            postRepo.deletePost(post).onSuccessWithData {
                updateUserMessage(it)
            }.onFailure {
                updateUserMessage(it)
            }
        }
    }

    private fun updateUserMessage(message: Error) {
        println("updateUserMessage: $message")
        _state.update { it.copy(actionResult = message) }
    }

    private fun updateLoading(isLoading: Boolean) {
        println("updateLoading: $isLoading")
        _state.update { it.copy(isLoading = isLoading) }
    }

    private fun updatePosts(posts: List<Post>) {
        _state.update { it.copy(posts = posts) }
    }

    private fun updateCurrentUser(user: User) {
        _state.update { it.copy(user = user) }
    }

    override fun onCleared() {
        super.onCleared()
        println("onCleared")
        updateUserMessage(PostResults.IDLE)
    }


}