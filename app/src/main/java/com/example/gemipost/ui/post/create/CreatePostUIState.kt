package com.example.gemipost.ui.post.create

import android.net.Uri
import com.example.gemipost.data.auth.source.remote.model.User
import com.example.gemipost.data.post.source.remote.model.PostAttachment
import com.example.gemipost.data.post.source.remote.model.Tag
import com.example.gemipost.ui.post.feed.FeedTab
import com.gp.socialapp.util.PostError


data class CreatePostUIState(
    var title: String = "",
    var body: String = "",
    var tags: List<Tag> = emptyList(),
    var files: List<Uri> = emptyList(),
    val userMessage: String = "",
    val isLoading: Boolean = false,
    val user : User? = null
)
