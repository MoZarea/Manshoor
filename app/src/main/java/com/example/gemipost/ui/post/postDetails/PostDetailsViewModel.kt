package com.example.gemipost.ui.post.postDetails

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gemipost.data.auth.repository.AuthenticationRepository
import com.example.gemipost.data.auth.source.remote.model.User
import com.example.gemipost.data.post.repository.PostRepository
import com.example.gemipost.data.post.repository.ReplyRepository
import com.example.gemipost.data.post.source.remote.model.NestedReply
import com.example.gemipost.data.post.source.remote.model.Post
import com.example.gemipost.data.post.source.remote.model.Reply
import com.example.gemipost.data.post.util.ToNestedReplies.toNestedReplies
import com.example.gemipost.ui.post.feed.PostEvent
import com.example.gemipost.ui.post.feed.ReplyEvent
import com.example.gemipost.utils.AuthResults
import com.example.gemipost.utils.Error
import com.example.gemipost.utils.PostResults
import com.example.gemipost.utils.urlToBitmap
import com.gp.socialapp.util.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PostDetailsViewModel(
    private val postRepo: PostRepository,
    private val replyRepo: ReplyRepository,
    private val authRepo: AuthenticationRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(PostDetailsUiState())
    val uiState = _uiState.asStateFlow()
    fun initScreenModel(postId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            authRepo.getSignedInUser().let { result ->
                println("zezo:getSignedInUser: $result")
                result.onSuccessWithData { data ->
                    updateCurrentUser(data)
                    updateLoginResults(AuthResults.LOGIN_SUCCESS)
                }.onFailure {
                    updateLoginResults(it)
                }
            }
            getPost(postId)
        }
    }

    private fun updateLoginResults(results: Error) {
        println("zezo:updateLoginResults: $results")
        _uiState.update { it.copy(loginStatus = results) }
    }

    private fun updateUserMessage(message: Error) {
        _uiState.update { it.copy(actionResult = message) }
    }

    private fun updateCurrentUser(user: User) {
        _uiState.update { it.copy(currentUser = user) }
    }

    private fun getPost(postId: String) {
        viewModelScope.launch {
            postRepo.fetchPostById(postId).let { result ->
                result.onLoading {
                    updateLoading(true)
                }.onFailure {
                    updateUserMessage(it)
                }.onSuccessWithData { post ->
                    updatePost(
                        post.copy(
                            upvoted = if (uiState.value.currentUser.id in post.upvoted) listOf("") else emptyList(),
                            downvoted = if (uiState.value.currentUser.id in post.downvoted) listOf("") else emptyList()
                        )
                    )

                    getRepliesById(postId)
                }
            }

        }
    }

    private fun updatePost(post: Post) {
        _uiState.update { it.copy(post = post) }
    }

    private fun updateLoading(state: Boolean) {
        _uiState.update { it.copy(isLoading = state) }

    }

    private fun updateReplies(nestedReplies: List<NestedReply>) {
        _uiState.update { it.copy(currentReplies = nestedReplies) }
    }

    private fun getRepliesById(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            replyRepo.getReplies(id).collect { result ->
                when (result) {
                    is Result.Success -> {
                        val nestedReplies = result.data.toNestedReplies()
                        updateReplies(nestedReplies)
                        updateLoading(false)
                    }

                    is Result.Error -> {
                        updateLoading(false)
                        updateUserMessage(result.message)
                    }

                    is Result.Loading -> {
                        updateLoading(true)
                    }
                }
            }
        }
    }

    private fun createReply(reply: Reply) {
        if (uiState.value.loginStatus != AuthResults.LOGIN_SUCCESS) return
        viewModelScope.launch(Dispatchers.IO) {
            replyRepo.createReply(
                reply.copy(
                    authorName = _uiState.value.currentUser.name,
                    authorImageLink = _uiState.value.currentUser.profilePictureURL
                )
            ).let { result ->
                when (result) {
                    is Result.Success -> {
                        getRepliesById(reply.postId)
                        getPost(reply.postId)
                    }

                    else -> Unit
                }
            }
        }
    }

    private fun reportReply(reply: Reply) {
        if (uiState.value.loginStatus != AuthResults.LOGIN_SUCCESS) return
        viewModelScope.launch(Dispatchers.IO) {
            replyRepo.reportReply(reply.id, reply.content).onSuccess {
                Log.d("seerde", "Reply reported")
            }.onFailure {
                Log.d("seerde", "Reply not reported")
            }
        }
    }


    private fun upvotePost(post: Post) {
        if (uiState.value.loginStatus != AuthResults.LOGIN_SUCCESS) return
        viewModelScope.launch(Dispatchers.IO) {
            val result = postRepo.upvotePost(post, _uiState.value.currentUser.id)
            when (result) {
                is Result.Error -> {
                    updateUserMessage(result.message)
                }

                Result.Loading -> {
                    updateLoading(true)
                }

                is Result.Success -> {
                    getPost(post.id)
                }
            }
        }
    }

    private fun downvotePost(post: Post) {
        if (uiState.value.loginStatus != AuthResults.LOGIN_SUCCESS) return
        viewModelScope.launch(Dispatchers.IO) {
            val result = postRepo.downvotePost(post, _uiState.value.currentUser.id)
            when (result) {
                is Result.Success -> {
                    updateLoading(false)
                    getPost(post.id)
                }

                is Result.Error -> {
                    updateUserMessage(result.message)
                }

                Result.Loading -> {
                    updateLoading(true)
                }
            }
        }
    }

    private fun deletePost(post: Post) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = postRepo.deletePost(post)
            result.onSuccessWithData {
                updateUserMessage(it)
            }
                .onFailure {
                    updateUserMessage(it)
                }
                .onLoading {
                    updateLoading(true)
                }
        }
    }


    private fun upvoteReply(reply: Reply) {
        if (uiState.value.loginStatus != AuthResults.LOGIN_SUCCESS) return
        viewModelScope.launch(Dispatchers.IO) {
            replyRepo.upvoteReply(reply.id, currentUserId = _uiState.value.currentUser.id)
                .let { result ->
                    result.onSuccessWithData {
                        updateLoading(false)
                        getRepliesById(reply.postId)
                    }
                        .onLoading {
                            updateLoading(true)
                        }
                        .onFailure { updateUserMessage(it) }
                }
        }
    }

    private fun downvoteReply(reply: Reply) {
        if (uiState.value.loginStatus != AuthResults.LOGIN_SUCCESS) return
        viewModelScope.launch(Dispatchers.IO) {
            replyRepo.downvoteReply(reply.id, _uiState.value.currentUser.id).let { result ->
                result.onSuccessWithData {
                    updateLoading(false)
                    getRepliesById(reply.postId)
                }
                    .onFailure { updateUserMessage(it) }
                    .onLoading { updateLoading(true) }
            }
        }
    }

    private fun deleteReply(reply: Reply) {
        viewModelScope.launch(Dispatchers.IO) {
            replyRepo.deleteReply(reply.id, reply.postId).let { result ->
                result.onSuccessWithData {
                    updateLoading(false)
                    getRepliesById(reply.postId)
                    getPost(reply.postId)
                }.onFailure {
                    updateUserMessage(it)
                }.onLoading { updateLoading(true) }
            }
        }
    }

    private fun updateReply(reply: Reply) {
        viewModelScope.launch(Dispatchers.IO) {
            replyRepo.updateReply(reply.id, reply.content).let { result ->
                result.onSuccessWithData {
                    updateLoading(false)
                    getRepliesById(reply.postId)
                }.onFailure {
                    updateUserMessage(it)
                }.onLoading { updateLoading(true) }
            }
        }
    }

    fun handlePostEvent(event: PostEvent) {
        when (event) {
            is PostEvent.OnPostDeleted -> deletePost(event.post)
            is PostEvent.OnPostUpVoted -> upvotePost(event.post)
            is PostEvent.OnPostDownVoted -> downvotePost(event.post)
            is PostEvent.OnCommentAdded -> {
                val reply = Reply(
                    postId = event.postId,
                    parentReplyId = "-1",
                    depth = 0,
                    content = event.text,
                    authorID = _uiState.value.currentUser.id,
                )
                println("reply in screen model: $reply")
                createReply(reply)
            }

            else -> {}
        }
    }

    private fun reportPost(post: Post, context: Context) {
        if (uiState.value.loginStatus != AuthResults.LOGIN_SUCCESS) return
        viewModelScope.launch(Dispatchers.IO) {
            val images =
                urlToBitmap(
                    scope = this@launch,
                    imageURLs = post.attachments,
                    context = context,
                )

            postRepo.reportPost(post.id, post.title, post.body, images).onSuccess {
                Log.d("seerde", "Post reported")
            }.onFailure {
                Log.d("seerde", "Post not reported")
            }
        }
    }


    fun handleReplyEvent(event: ReplyEvent) {
        when (event) {
            is ReplyEvent.OnReplyDeleted -> deleteReply(event.reply)
            is ReplyEvent.OnReplyUpVoted -> upvoteReply(event.reply)
            is ReplyEvent.OnReplyDownVoted -> downvoteReply(event.reply)
            is ReplyEvent.OnReplyAdded -> {
                val reply = Reply(
                    postId = event.reply.postId,
                    parentReplyId = event.reply.id,
                    depth = event.reply.depth + 1,
                    content = event.text,
                    authorID = _uiState.value.currentUser.id,
                    authorName = _uiState.value.currentUser.name,
                    authorImageLink = _uiState.value.currentUser.profilePictureURL
                )
                println("nested reply in screen model: $reply")
                createReply(reply)
            }

            is ReplyEvent.OnReplyReported -> {
                reportReply(event.reply)
            }

            is ReplyEvent.OnReplyEdited -> {
                updateReply(event.reply)
            }

            else -> {}
        }
    }

    fun resetState() {
        _uiState.update { it.copy(actionResult = PostResults.IDLE) }
    }
}