package com.example.gemipost.ui.post.create

import android.net.Uri
import com.example.gemipost.data.auth.source.remote.model.User
import com.example.gemipost.data.post.source.remote.model.Tag
import com.example.gemipost.utils.Error
import com.example.gemipost.utils.PostResults


data class CreatePostUIState(
    var title: String = "",
    var body: String = "",
    var tags: List<Tag> = emptyList(),
    var files: List<Uri> = emptyList(),
    val actionResult: Error = PostResults.IDLE,
    val isLoading: Boolean = false,
    val user : User? = null
)
