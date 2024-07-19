package com.gp.socialapp.presentation.post.edit

import com.example.gemipost.data.post.source.remote.model.PostAttachment
import com.example.gemipost.data.post.source.remote.model.Tag

sealed interface EditPostAction {
    data object Init : EditPostAction
    data object NavigateBack : EditPostAction
    data object OnAddImageClicked : EditPostAction
    data object OnAddVideoClicked : EditPostAction
    data class OnFileAdded(val file: PostAttachment) : EditPostAction
    data object OnAddFileClicked : EditPostAction
    data class OnPreviewClicked(val file: PostAttachment) : EditPostAction
    data class OnTitleChanged(val title: String) : EditPostAction
    data class OnContentChanged(val content: String) : EditPostAction
    data object OnApplyEditClicked : EditPostAction
    data class OnTagAdded(val tags: Set<Tag>) : EditPostAction
    data class OnTagRemoved(val tag: Tag) : EditPostAction
    data class OnFileRemoved(val file: PostAttachment) : EditPostAction


}