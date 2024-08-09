package com.example.gemipost.data.post.source.remote.model

data class NestedReply(
    var reply: Reply? = null,
    val childrenCount: Int = 0,
    var replies: List<NestedReply> = emptyList()
)
