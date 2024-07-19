package com.gp.socialapp.presentation.post.postDetails

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.gp.socialapp.data.auth.repository.AuthenticationRepository
import com.example.gemipost.data.post.repository.PostRepository
import com.example.gemipost.data.post.repository.ReplyRepository
import com.example.gemipost.data.post.source.remote.model.Post
import com.example.gemipost.data.post.source.remote.model.PostAttachment
import com.example.gemipost.data.post.source.remote.model.Reply
import com.example.gemipost.data.post.util.ToNestedReplies.toNestedReplies
import com.gp.socialapp.presentation.material.utils.MimeType
import com.gp.socialapp.presentation.post.feed.PostEvent
import com.gp.socialapp.presentation.post.feed.ReplyEvent
import com.gp.socialapp.util.DispatcherIO
import com.gp.socialapp.util.Result
import io.github.aakira.napier.Napier
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PostDetailsScreenModel(
    private val postRepo: PostRepository,
    private val replyRepo: ReplyRepository,
    private val authRepo: AuthenticationRepository
) : ScreenModel {
    private val _uiState = MutableStateFlow(PostDetailsUiState())
    val uiState = _uiState.asStateFlow()
    fun initScreenModel(post: Post) {
        screenModelScope.launch(DispatcherIO) {
            authRepo.getSignedInUser().let { result ->
                when (result) {
                    is Result.Success -> {
                        _uiState.update { it.copy(post = post, currentUserId = result.data.id) }
                    }

                    is Result.Error -> {
                        Napier.e("Error: ${result.message}")
                        //TODO Handle error
                    }

                    else -> Unit
                }
            }
            getRepliesById(post.id)
        }
    }

    private fun getRepliesById(id: String) {
        screenModelScope.launch(DispatcherIO) {
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
        screenModelScope.launch(DispatcherIO) {
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
        screenModelScope.launch(DispatcherIO) {
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
        screenModelScope.launch(DispatcherIO) {
//            TODO
        }
    }

    private fun upvotePost(post: Post) {
        screenModelScope.launch(DispatcherIO) {
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
        screenModelScope.launch(DispatcherIO) {
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
        screenModelScope.launch(DispatcherIO) {
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
        screenModelScope.launch(DispatcherIO) {
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
        screenModelScope.launch(DispatcherIO) {
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
        screenModelScope.launch(DispatcherIO) {
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
        screenModelScope.launch(DispatcherIO) {
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
        screenModelScope.launch(DispatcherIO) {
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
        screenModelScope.launch {
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
        screenModelScope.launch(DispatcherIO) {
            val mimeType = MimeType.getMimeTypeFromFileName(attachment.name)
            val fullMimeType = MimeType.getFullMimeType(mimeType)
            postRepo.openAttachment(attachment.url, fullMimeType)
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