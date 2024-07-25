package com.example.gemipost.ui.post.feed

import com.example.gemipost.data.auth.source.remote.model.User
import com.example.gemipost.data.post.source.remote.model.Post
import com.example.gemipost.data.post.source.remote.model.Tag
import com.gp.socialapp.util.PostError
import com.gp.socialapp.util.Result

data class FeedUiState(
    val posts: List<Post> = emptyList(),
    val user: User = User(),
    val userMessage: String = ""
)


