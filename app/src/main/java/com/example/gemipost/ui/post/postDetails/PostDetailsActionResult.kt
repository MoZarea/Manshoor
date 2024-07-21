package com.example.gemipost.ui.post.postDetails

sealed class PostDetailsActionResult {
    data object PostDeleted : PostDetailsActionResult()
    data object PostReported : PostDetailsActionResult()
    data object PostUpdated : PostDetailsActionResult()
    data object ReplyReported : PostDetailsActionResult()
    data object ReplyUpdated : PostDetailsActionResult()
    data object ReplyDeleted : PostDetailsActionResult()
    data object NoActionResult : PostDetailsActionResult()
    data class NetworkError(val message: String) : PostDetailsActionResult()
    data object UnknownActionResult : PostDetailsActionResult()
}