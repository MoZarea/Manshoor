package com.example.gemipost.ui.post.postDetails.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.gemipost.data.post.source.remote.model.NestedReply
import com.example.gemipost.ui.post.feed.ReplyEvent

@Composable
fun ReplyTopRow(
    nestedReply: NestedReply,
    replyEvent: (ReplyEvent) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        ReplyIcon(nestedReply, replyEvent)
        ReplyAuthorName(nestedReply)
        ReplyDate(nestedReply)

    }
}