package com.example.gemipost.data.post.util

import com.example.gemipost.data.post.source.remote.model.NestedReply
import com.example.gemipost.data.post.source.remote.model.Reply

object ToNestedReplies {
    fun List<Reply>.toNestedReplies(): List<NestedReply> {
        val nestedRepliesList = buildNestedReplies(this, "-1")
        return nestedRepliesList
    }

    private fun buildNestedReplies(
        replies: List<Reply>,
        parentReplyId: String
    ): List<NestedReply> {
        return replies
            .filter { it.parentReplyId == parentReplyId }
            .map { reply ->
                val nestedReplies = buildNestedReplies(replies, reply.id)
                NestedReply(
                    reply = reply,
                    replies = nestedReplies
                )
            }
    }
}