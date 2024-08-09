package com.example.gemipost.ui.post.postDetails.components

import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import com.example.gemipost.data.post.source.remote.model.NestedReply
import com.example.gemipost.data.post.source.remote.model.Reply
import com.example.gemipost.ui.post.feed.ReplyEvent


fun LazyListScope.NestedReplyItem(
    nestedReply: NestedReply,
    isLast: Boolean = false,
    level: Int = 0,
    onReplyEvent: (ReplyEvent) -> Unit,
    currentUserId: String
) {
    item {
        val ltrLayoutDirection = remember { LayoutDirection.Ltr }
        CompositionLocalProvider(LocalLayoutDirection provides ltrLayoutDirection) {
            ReplyItem(nestedReply.reply ?: Reply(), isLast, currentUserId, level, onReplyEvent)
        }
    }
    RepliesList(nestedReply.replies, level + 1, onReplyEvent, currentUserId)
}