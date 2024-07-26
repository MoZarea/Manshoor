package com.example.gemipost.ui.post.edit

import android.net.Uri
import com.example.gemipost.data.post.source.remote.model.Post
import com.example.gemipost.data.post.source.remote.model.Tag

data class EditPostUIState(
    val post: Post = Post(),
    val title: String = "",
    val body: String = "",
    val postTags: Set<Tag> = emptySet(),
    val postAttachments: List<Uri> = emptyList(),
    val userMessage :String = "",
    val isLoading :Boolean = false,
)