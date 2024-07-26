package com.example.gemipost.ui.post.edit

import android.net.Uri
import com.example.gemipost.data.post.source.remote.model.Tag

sealed interface EditPostAction {
    data class OnFileAdded(val file: Uri) : EditPostAction
    data class OnTitleChanged(val title: String) : EditPostAction
    data class OnContentChanged(val content: String) : EditPostAction
    data object OnApplyEditClicked : EditPostAction
    data class OnTagAdded(val tags: Tag) : EditPostAction
    data class OnTagRemoved(val tag: Tag) : EditPostAction
    data class OnFileRemoved(val file: Uri) : EditPostAction


}