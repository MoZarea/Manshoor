package com.example.gemipost.ui.post.postDetails.components

import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.runtime.Composable
import com.example.gemipost.data.post.source.remote.model.NestedReply
import com.example.gemipost.ui.post.feed.ReplyEvent


fun LazyListScope.RepliesList(replies: List<NestedReply>, level: Int = 0, onReplyEvent: (ReplyEvent)->Unit, currentUserId: String) {
    replies.forEach { reply ->
        NestedReplyItem(reply, level ,onReplyEvent, currentUserId)
    }
}