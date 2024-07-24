package com.example.gemipost.ui.post.create

import android.net.Uri
import com.example.gemipost.data.post.source.remote.model.PostAttachment
import com.example.gemipost.data.post.source.remote.model.Tag
import com.example.gemipost.ui.post.create.component.ImageMetadata

sealed interface CreatePostEvents {

    data class OnTitleChanged(val newTitle: String) : CreatePostEvents
    data class OnBodyChanged(val newBody: String) : CreatePostEvents
    data object OnCreatePostClicked : CreatePostEvents
    data class OnAddTag(val tag: Tag) : CreatePostEvents
    data class OnRemoveTag(val tag: Tag) : CreatePostEvents
    data class OnAddFile(val uri: Uri) : CreatePostEvents
    data class OnRemoveFile(val uri: Uri) : CreatePostEvents


}