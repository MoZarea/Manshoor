package com.example.gemipost.ui.post.feed

import com.example.gemipost.data.post.source.remote.model.Post
import com.example.gemipost.data.post.source.remote.model.PostAttachment
import com.example.gemipost.data.post.source.remote.model.Reply
import com.example.gemipost.data.post.source.remote.model.Tag

sealed class PostEvent {
    data class OnPostAuthorClicked(val userId: String) : PostEvent()
    data class OnPostClicked(val post: Post) : PostEvent()
    data class OnPostDeleted(val post: Post) : PostEvent()
    data class OnPostEdited(val post: Post) : PostEvent()
    data class OnPostUpVoted(val post: Post) : PostEvent()
    data class OnPostDownVoted(val post: Post) : PostEvent()
    data class OnPostReported(val post: Post) : PostEvent()
    data class OnPostShareClicked(val post: Post) : PostEvent()
    object OnAddPost : PostEvent()
    data class OnTagClicked(val tag: Tag) : PostEvent()
    data class OnAttachmentClicked(val attachment: PostAttachment) : PostEvent()
    data class OnImageClicked(val image: PostAttachment) : PostEvent()
    data class OnCommentClicked(val post: Post) : PostEvent()
    data class OnCommentAdded(
        val text: String,
        val postId: String,
    ) : PostEvent()

    object Initial : PostEvent()
    data class OnViewFilesAttachmentClicked(val files: List<PostAttachment>) : PostEvent()

}

sealed class ReplyEvent {
    data class OnReportReply(val reply: Reply) : ReplyEvent()
    data class OnReplyReported(val reply: Reply) : ReplyEvent()
    data class OnShareReply(val reply: Reply) : ReplyEvent()
    data class OnReplyClicked(val reply: Reply) : ReplyEvent()
    data class OnReplyDeleted(val reply: Reply) : ReplyEvent()
    data class OnReplyEdited(val reply: Reply) : ReplyEvent()
    data class OnEditReply(val reply: Reply) : ReplyEvent()
    data class OnReplyUpVoted(val reply: Reply) : ReplyEvent()
    data class OnReplyDownVoted(val reply: Reply) : ReplyEvent()
    data class OnAddReply(val reply: Reply) : ReplyEvent()
    data class OnReplyAuthorClicked(val userId: String) : ReplyEvent()
    object Initial : ReplyEvent()
    data class OnReplyAdded(
        val text: String, val reply: Reply
    ) : ReplyEvent()
}