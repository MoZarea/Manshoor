package com.example.gemipost.ui.post.postDetails

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gemipost.data.auth.repository.AuthenticationRepository
import com.example.gemipost.data.post.repository.PostRepository
import com.example.gemipost.data.post.repository.ReplyRepository
import com.example.gemipost.data.post.source.remote.model.Post
import com.example.gemipost.data.post.source.remote.model.PostAttachment
import com.example.gemipost.data.post.source.remote.model.Reply
import com.example.gemipost.data.post.util.ToNestedReplies.toNestedReplies
import com.example.gemipost.ui.post.feed.PostEvent
import com.example.gemipost.ui.post.feed.ReplyEvent
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
            //todo
            authRepo.getSignedInUser().let { result ->
                when (result) {
                    is Result.Success -> {
//                        _uiState.update { it.copy(post = post, currentUserId = result.data.id) }
                    }

                    is Result.Error -> {
                        Log.d("seerde","Error: ${result.message}")
                        //TODO Handle error
                    }

                    else -> Unit
                }
            }
            getRepliesById(postId)
        }
    }

    private fun getRepliesById(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            replyRepo.getReplies(id).collect { result ->
                when (result) {
                    is Result.Success -> {
                        val nestedReplies = result.data.toNestedReplies()
                        _uiState.update {
                            it.copy(
                                currentReplies = nestedReplies,
                                isLoading = false
                            )
                        }
                    }

                    is Result.Error -> {
                        _uiState.update {
                            it.copy(
                                actionResult = PostDetailsActionResult.NetworkError(
                                    result.message.userMessage
                                ), isLoading = false
                            )
                        }
                    }

                    is Result.Loading -> {
                        _uiState.update { it.copy(isLoading = true) }
                    }
                }
            }
        }
    }

    private fun insertReply(reply: Reply) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = replyRepo.insertReply(reply)
            when (result) {
                is Result.Success -> {
                    getRepliesById(reply.postId)
                }

                is Result.Error -> {
                    _uiState.update {
                        it.copy(
                            actionResult = PostDetailsActionResult.NetworkError(
                                result.message.userMessage
                            )
                        )
                    }
                }

                Result.Loading -> {
                    // TODO
                }
            }
        }
    }

    private fun reportReply(reply: Reply) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = replyRepo.reportReply(reply.id, _uiState.value.currentUserId)
            when (result) {
                is Result.Success -> {
                    _uiState.update { it.copy(actionResult = PostDetailsActionResult.ReplyReported) }
                }

                is Result.Error -> {
                    _uiState.update {
                        it.copy(
                            actionResult = PostDetailsActionResult.NetworkError(
                                result.message.userMessage
                            )
                        )
                    }
                }

                Result.Loading -> {
                    // TODO
                }
            }
        }
    }

    private fun updatePost() {
        viewModelScope.launch(Dispatchers.IO) {
//            TODO
        }
    }

    private fun upvotePost(post: Post) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = postRepo.upvotePost(post, _uiState.value.currentUserId)
            when (result) {
                is Result.Error -> {
                    _uiState.update {
                        it.copy(
                            actionResult = PostDetailsActionResult.NetworkError(
                                result.message.userMessage
                            )
                        )
                    }
                }

                Result.Loading -> {
                    // TODO
                }

                is Result.Success -> {
                    updatePost()
                }
            }
        }
    }

    private fun downvotePost(post: Post) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = postRepo.downvotePost(post, _uiState.value.currentUserId)
            when (result) {
                is Result.Success -> {
                    updatePost()
                }

                is Result.Error -> {
                    _uiState.update {
                        it.copy(
                            actionResult = PostDetailsActionResult.NetworkError(
                                result.message.userMessage
                            )
                        )
                    }
                }

                Result.Loading -> {
                    // TODO
                }
            }
        }
    }

    private fun deletePost(post: Post) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = postRepo.deletePost(post)
            when (result) {
                is Result.Success -> {
                    _uiState.update { it.copy(actionResult = PostDetailsActionResult.PostDeleted) }
                }

                is Result.Error -> {
                    _uiState.update {
                        it.copy(
                            actionResult = PostDetailsActionResult.NetworkError(
                                result.message.userMessage
                            )
                        )
                    }
                }

                Result.Loading -> {
                    // TODO

                }
            }
        }
    }

    private fun updatePost(post: Post) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = postRepo.updatePost(post)
            when (result) {
                is Result.Success -> {
                    updatePost()
                    _uiState.update { it.copy(actionResult = PostDetailsActionResult.PostUpdated) }
                }

                is Result.Error -> {
                    _uiState.update {
                        it.copy(
                            actionResult = PostDetailsActionResult.NetworkError(
                                result.message.userMessage
                            )
                        )
                    }
                }

                Result.Loading -> {
                    // TODO
                }
            }
        }
    }

    private fun upvoteReply(reply: Reply) {
        viewModelScope.launch(Dispatchers.IO) {
            val result =
                replyRepo.upvoteReply(reply.id, currentUserId = _uiState.value.currentUserId)
            when (result) {
                is Result.Success -> {
                    getRepliesById(reply.postId)
                }

                is Result.Error -> {
                    _uiState.update {
                        it.copy(
                            actionResult = PostDetailsActionResult.NetworkError(
                                result.message.userMessage
                            )
                        )
                    }
                }

                Result.Loading -> {
                    // TODO
                }
            }
        }
    }

    private fun downvoteReply(reply: Reply) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = replyRepo.downvoteReply(reply.id, _uiState.value.currentUserId)
            when (result) {
                is Result.Success -> {
                    getRepliesById(reply.postId)
                }

                is Result.Error -> {
                    _uiState.update {
                        it.copy(
                            actionResult = PostDetailsActionResult.NetworkError(
                                result.message.userMessage
                            )
                        )
                    }
                }

                Result.Loading -> {
                    // TODO
                }
            }
        }
    }

    private fun deleteReply(reply: Reply) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = replyRepo.deleteReply(reply.id)
            when (result) {
                is Result.Success -> {
                    getRepliesById(reply.postId)
                    _uiState.update { it.copy(actionResult = PostDetailsActionResult.ReplyDeleted) }
                }

                is Result.Error -> {
                    _uiState.update {
                        it.copy(
                            actionResult = PostDetailsActionResult.NetworkError(
                                result.message.userMessage
                            )
                        )
                    }
                }

                Result.Loading -> {
                    // TODO
                }
            }
        }

    }

    private fun updateReply(reply: Reply) {
        viewModelScope.launch(Dispatchers.IO) {
            replyRepo.updateReply(reply.id, reply.content).let { result ->
                when (result) {
                    is Result.Success -> {
                        getRepliesById(reply.postId)
                        _uiState.update { it.copy(actionResult = PostDetailsActionResult.ReplyUpdated) }
                    }

                    is Result.Error -> {
                        _uiState.update {
                            it.copy(
                                actionResult = PostDetailsActionResult.NetworkError(
                                    result.message.userMessage
                                )
                            )
                        }
                    }

                    Result.Loading -> {
                        // TODO
                    }
                }
            }
        }
    }

    fun resetActionResult() {
        viewModelScope.launch {
            _uiState.update { it.copy(actionResult = PostDetailsActionResult.NoActionResult) }
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
                    authorID = _uiState.value.currentUserId,
                )
                println("reply in screen model: $reply")
                insertReply(reply)
            }

            is PostEvent.OnAttachmentClicked -> {
                openAttachment(event.attachment)
            }

            else -> {}
        }
    }

    private fun openAttachment(attachment: PostAttachment) {
        viewModelScope.launch(Dispatchers.IO) {
            //todo
            //            val mimeType = MimeType.getMimeTypeFromFileName(attachment.name)
//            val fullMimeType = MimeType.getFullMimeType(mimeType)
//            postRepo.openAttachment(attachment.url, fullMimeType)
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
                    authorID = _uiState.value.currentUserId,
                )
                println("nested reply in screen model: $reply")
                insertReply(reply)
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
}