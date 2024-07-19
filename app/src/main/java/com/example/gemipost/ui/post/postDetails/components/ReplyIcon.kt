package com.gp.socialapp.presentation.post.postDetails.components

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.gemipost.data.post.source.remote.model.NestedReply
import com.gp.socialapp.presentation.chat.creategroup.components.CircularAvatar
import com.gp.socialapp.presentation.post.feed.ReplyEvent
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.solid.UserCircle

@Composable
 fun ReplyIcon(
    nestedReply: NestedReply,
    replyEvent: (ReplyEvent) -> Unit
) {
    CircularAvatar(
        modifier = Modifier.padding(start = 8.dp, end = 8.dp),
        imageURL = nestedReply.reply?.authorImageLink ?: "",
        size = 26.dp,
        placeHolderImageVector = FontAwesomeIcons.Solid.UserCircle,
        onClick = {
            replyEvent(
                ReplyEvent.OnReplyAuthorClicked(
                    nestedReply.reply?.authorID ?: ""
                )
            )
        }
    )
}