package com.example.gemipost.ui.post.postDetails.components

import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import com.example.gemipost.data.post.source.remote.model.NestedReply
import com.gp.socialapp.presentation.post.feed.ReplyEvent


fun LazyListScope.NestedReplyItem(comment: NestedReply, level: Int, onReplyEvent: (ReplyEvent)->Unit, currentUserId: String) {
    item {
        val ltrLayoutDirection = remember { LayoutDirection.Ltr }
        CompositionLocalProvider(LocalLayoutDirection provides ltrLayoutDirection) {
            ReplyItem(comment, currentUserId, level, onReplyEvent)
        }
    }
    RepliesList(comment.replies, level + 1, onReplyEvent, currentUserId)
}