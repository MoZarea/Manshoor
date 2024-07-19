package com.gp.socialapp.presentation.post.postDetails.components

import androidx.compose.foundation.lazy.LazyListScope
import com.example.gemipost.data.post.source.remote.model.NestedReply
import com.gp.socialapp.presentation.post.feed.ReplyEvent


fun LazyListScope.RepliesList(replies: List<NestedReply>, level: Int = 0, onReplyEvent: (ReplyEvent)->Unit, currentUserId: String) {
    replies.forEach { reply ->
        NestedReplyItem(reply, level ,onReplyEvent, currentUserId)
    }
}