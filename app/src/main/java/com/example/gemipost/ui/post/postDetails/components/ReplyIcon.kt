package com.example.gemipost.ui.post.postDetails.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.gemipost.data.post.source.remote.model.NestedReply
import com.gp.socialapp.presentation.post.feed.ReplyEvent

@Composable
 fun ReplyIcon(
    nestedReply: NestedReply,
    replyEvent: (ReplyEvent) -> Unit
) {
    CircularAvatar(
        modifier = Modifier.padding(start = 8.dp, end = 8.dp),
        imageURL = nestedReply.reply?.authorImageLink ?: "",
        size = 26.dp,
        placeHolderImageVector = Icons.Default.AccountCircle,
        onClick = {
            replyEvent(
                ReplyEvent.OnReplyAuthorClicked(
                    nestedReply.reply?.authorID ?: ""
                )
            )
        }
    )
}