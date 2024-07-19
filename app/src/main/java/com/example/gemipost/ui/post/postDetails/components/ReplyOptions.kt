package com.gp.socialapp.presentation.post.postDetails.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.ThumbDown
import androidx.compose.material.icons.filled.ThumbUp
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
import androidx.compose.ui.unit.dp
import com.example.gemipost.data.post.source.remote.model.NestedReply
import com.example.gemipost.data.post.source.remote.model.Reply
import com.gp.socialapp.presentation.post.feed.ReplyEvent
import com.gp.socialapp.presentation.post.feed.components.icons.FeedIcons
import com.gp.socialapp.presentation.post.feed.components.icons.feedicons.Comment
import com.gp.socialapp.util.ModerationSafety
import org.jetbrains.compose.resources.stringResource
import socialmultiplatform.composeapp.generated.resources.Res
import socialmultiplatform.composeapp.generated.resources.delete
import socialmultiplatform.composeapp.generated.resources.edit
import socialmultiplatform.composeapp.generated.resources.report
import socialmultiplatform.composeapp.generated.resources.share

@Composable
fun ReplyOptions(
    nestedReply: NestedReply,
    currentUserId: String,
    replyEvent: (ReplyEvent) -> Unit
) {
    if (nestedReply.reply?.moderationStatus != ModerationSafety.UNSAFE_REPLY.name)

        Row(
            modifier = Modifier.fillMaxWidth().padding(
                start = 4.dp,
                end = 4.dp,
            ).sizeIn(maxHeight = 28.dp),
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
                val dropDownItems = if (nestedReply.reply?.authorID == currentUserId) {
                    listOf(ReplyDropDownItem(stringResource(Res.string.edit)) {
                        replyEvent(
                            ReplyEvent.OnEditReply(
                                reply = nestedReply.reply ?: Reply()
                            )
                        )
                    }, ReplyDropDownItem(stringResource(Res.string.delete)) {
                        replyEvent(
                            ReplyEvent.OnReplyDeleted(
                                reply = nestedReply.reply ?: Reply()
                            )
                        )
                    }, ReplyDropDownItem(stringResource(Res.string.share)) {
                        replyEvent(
                            ReplyEvent.OnShareReply(
                                reply = nestedReply.reply ?: Reply()
                            )
                        )
                    }, ReplyDropDownItem(stringResource(Res.string.report)) {
                        replyEvent(
                            ReplyEvent.OnReportReply(
                                reply = nestedReply.reply ?: Reply()
                            )
                        )
                    })
                } else {
                    listOf(ReplyDropDownItem(stringResource(Res.string.share)) {
                        replyEvent(
                            ReplyEvent.OnShareReply(
                                reply = nestedReply.reply ?: Reply()
                            )
                        )
                    }, ReplyDropDownItem(stringResource(Res.string.report)) {
                        replyEvent(
                            ReplyEvent.OnReportReply(
                                reply = nestedReply.reply ?: Reply()
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
                replyEvent(ReplyEvent.OnAddReply(reply = nestedReply.reply ?: Reply()))
            }) {
                Icon(
                    imageVector = FeedIcons.Comment,
                    contentDescription = "Comment",
                    tint = MaterialTheme.colorScheme.onPrimaryContainer,
                    modifier = Modifier.padding(horizontal = 8.dp).size(20.dp)
                )
            }
            IconButton(onClick = {
                replyEvent(
                    ReplyEvent.OnReplyUpVoted(
                        reply = nestedReply.reply ?: Reply()
                    )
                )
            }) {
                Icon(
                    imageVector = Icons.Filled.ThumbUp, contentDescription = "Like",
                    tint = MaterialTheme.colorScheme.onPrimaryContainer,

                    )
            }
            Text(text = (nestedReply.reply?.votes ?: 0).toString())
            IconButton(onClick = {
                replyEvent(
                    ReplyEvent.OnReplyDownVoted(
                        reply = nestedReply.reply ?: Reply()
                    )
                )
            }) {
                Icon(
                    imageVector = Icons.Filled.ThumbDown, contentDescription = "Share",
                    tint = MaterialTheme.colorScheme.onPrimaryContainer,

                    )
            }
        }
}