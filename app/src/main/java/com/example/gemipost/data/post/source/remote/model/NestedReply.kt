package com.example.gemipost.data.post.source.remote.model

data class NestedReply(
    var reply: Reply? = null,
    var replies: List<NestedReply> = emptyList()
)
