package com.gp.socialapp.presentation.post.postDetails

import com.example.gemipost.data.post.source.remote.model.NestedReply
import com.example.gemipost.data.post.source.remote.model.Post
import com.example.gemipost.data.post.source.remote.model.Reply

data class PostDetailsUiState(
    val post: Post = Post(),
    val currentUserId: String = "",
    val isLoading: Boolean = false,
    val currentReplies: List<NestedReply> = emptyList(),
    val currentReply: Reply = Reply(),
    val actionResult: PostDetailsActionResult = PostDetailsActionResult.NoActionResult
)
