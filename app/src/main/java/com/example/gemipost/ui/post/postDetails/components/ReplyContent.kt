package com.example.gemipost.ui.post.postDetails.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.gemipost.data.post.source.remote.model.Reply
import com.example.gemipost.ui.post.feed.ReplyEvent

@Composable
fun ReplyContent(
    reply: Reply,
    replyEvent: (ReplyEvent) -> Unit,
    currentUserId: String
) {
    Column(
        modifier = Modifier.padding(4.dp)
    ) {
        ReplyTopRow(
            authorImageLink = reply.authorImageLink,
            authorId = reply.authorID,
            authorName = reply.authorName,
            createdAt = reply.createdAt,
            replyEvent = replyEvent
        )
        ReplyBody(
            body = reply.content,
            moderationStatus = reply.moderationStatus
        )
        ReplyOptions(reply, currentUserId, replyEvent)
    }
}
