package com.example.gemipost.ui.post.postDetails

import com.example.gemipost.data.auth.source.remote.model.User
import com.example.gemipost.data.post.source.remote.model.NestedReply
import com.example.gemipost.data.post.source.remote.model.Post
import com.example.gemipost.data.post.source.remote.model.Reply
import com.example.gemipost.utils.AuthResults
import com.example.gemipost.utils.Error
import com.example.gemipost.utils.PostResults

data class PostDetailsUiState(
    val post: Post = Post(),
    val currentUser: User = User(),
    val isLoading: Boolean = false,
    val currentReplies: List<NestedReply> = emptyList(),
    val currentReply: Reply = Reply(),
    val actionResult: Error = PostResults.IDLE,
    val loginStatus: Error = AuthResults.IDLE,
)
