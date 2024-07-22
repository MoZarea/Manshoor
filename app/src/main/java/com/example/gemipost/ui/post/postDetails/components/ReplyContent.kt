package com.example.gemipost.ui.post.postDetails.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.gemipost.data.post.source.remote.model.NestedReply
import com.example.gemipost.ui.post.feed.ReplyEvent

@Composable
fun ReplyContent(
    nestedReply: NestedReply,
    replyEvent: (ReplyEvent) -> Unit,
    currentUserId: String
) {
    Column(
        modifier = Modifier.padding(4.dp)
    ) {
        ReplyTopRow(nestedReply, replyEvent)
        ReplyBody(nestedReply)
        ReplyOptions(nestedReply, currentUserId, replyEvent)
    }
}
