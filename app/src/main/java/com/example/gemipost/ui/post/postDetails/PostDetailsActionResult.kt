package com.gp.socialapp.presentation.post.postDetails

sealed class PostDetailsActionResult {
    object PostDeleted : PostDetailsActionResult()
    object PostReported : PostDetailsActionResult()
    object PostUpdated : PostDetailsActionResult()
    object ReplyReported : PostDetailsActionResult()
    object ReplyUpdated : PostDetailsActionResult()
    object ReplyDeleted : PostDetailsActionResult()
    object NoActionResult : PostDetailsActionResult()
    data class NetworkError(val message: String) : PostDetailsActionResult()
    object UnknownActionResult : PostDetailsActionResult()
}