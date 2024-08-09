package com.example.gemipost.ui.post.postDetails.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Comment
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.ThumbDownAlt
import androidx.compose.material.icons.filled.ThumbUpAlt
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.gemipost.R
import com.example.gemipost.data.post.source.remote.model.NestedReply
import com.example.gemipost.data.post.source.remote.model.Reply
import com.example.gemipost.data.post.source.remote.model.isDownvoted
import com.example.gemipost.data.post.source.remote.model.isUpvoted
import com.example.gemipost.ui.post.feed.ReplyEvent
import com.gp.socialapp.util.ModerationSafety

@Composable
fun ReplyOptions(
    reply: Reply,
    currentUserId: String,
    replyEvent: (ReplyEvent) -> Unit
) {
    val upvoteColor = MaterialTheme.colorScheme.onPrimaryContainer
    val downvoteColor = MaterialTheme.colorScheme.error
    if (reply.moderationStatus != ModerationSafety.UNSAFE_REPLY.name)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = 4.dp,
                    end = 4.dp,
                )
                .sizeIn(maxHeight = 28.dp),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box {
                var visible by remember { mutableStateOf(false) }
                IconButton(onClick = {
                    visible = true
                }) {
                    Icon(
                        imageVector = Icons.Filled.MoreVert,
                        contentDescription = "More options",
                        tint = MaterialTheme.colorScheme.onPrimaryContainer,

                        )
                }
                val dropDownItems = if (reply.authorID == currentUserId) {
                    listOf(ReplyDropDownItem(stringResource(R.string.edit)) {
                        replyEvent(
                            ReplyEvent.OnEditReply(
                                reply = reply
                            )
                        )
                    }, ReplyDropDownItem(stringResource(R.string.delete)) {
                        replyEvent(
                            ReplyEvent.OnReplyDeleted(
                                reply = reply
                            )
                        )
                    }, ReplyDropDownItem(stringResource(R.string.share)) {
                        replyEvent(
                            ReplyEvent.OnShareReply(
                                reply = reply
                            )
                        )
                    }, ReplyDropDownItem(stringResource(R.string.report)) {
                        replyEvent(
                            ReplyEvent.OnReportReply(
                                reply = reply
                            )
                        )
                    })
                } else {
                    listOf(ReplyDropDownItem(stringResource(R.string.share)) {
                        replyEvent(
                            ReplyEvent.OnShareReply(
                                reply = reply
                            )
                        )
                    }, ReplyDropDownItem(stringResource(R.string.report)) {
                        replyEvent(
                            ReplyEvent.OnReportReply(
                                reply = reply
                            )
                        )
                    })
                }
                DropdownMenu(
                    expanded = visible,
                    onDismissRequest = { visible = false },
                ) {
                    dropDownItems.forEach { item ->
                        DropdownMenuItem(text = { Text(text = item.text) }, onClick = {
                            item.onClick()
                            visible = false
                        })
                    }
                }
            }
            IconButton(onClick = {
                replyEvent(ReplyEvent.OnAddReply(reply = reply))
            }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.Comment,
                    contentDescription = "Comment",
                    tint = MaterialTheme.colorScheme.onPrimaryContainer,
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .size(20.dp)
                )
            }
            IconButton(onClick = {
                replyEvent(
                    ReplyEvent.OnReplyUpVoted(
                        reply = reply
                    )
                )
            }) {
                Icon(
                    imageVector = Icons.Filled.ThumbUpAlt, contentDescription = "Like",
                    tint = if (reply.isUpvoted(currentUserId)) upvoteColor  else MaterialTheme.colorScheme.onPrimaryContainer,
                    )
            }
            Text(text = (reply.votes).toString())
            IconButton(onClick = {
                replyEvent(
                    ReplyEvent.OnReplyDownVoted(
                        reply = reply
                    )
                )
            }) {
                Icon(
                    imageVector = Icons.Filled.ThumbDownAlt, contentDescription = "Share",
                    tint = if (reply.isDownvoted(currentUserId)) downvoteColor else MaterialTheme.colorScheme.onPrimaryContainer,

                    )
            }
        }
}