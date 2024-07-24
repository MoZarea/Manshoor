package com.example.gemipost.ui.post.create

import com.example.gemipost.data.post.source.remote.model.PostAttachment
import com.example.gemipost.data.post.source.remote.model.Tag

sealed interface CreatePostEvents {

    data class OnTitleChanged(val newTitle: String) : CreatePostEvents
    data class OnBodyChanged(val newBody: String) : CreatePostEvents
    data object OnCreatePostClicked : CreatePostEvents
    data class OnAddTag(val tag: Tag) : CreatePostEvents
    data class OnRemoveTag(val tag: Tag) : CreatePostEvents
    data class OnAddFile(val postAttachment: PostAttachment) : CreatePostEvents
    data class OnRemoveFile(val postAttachment: PostAttachment) : CreatePostEvents


}