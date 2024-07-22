package com.example.gemipost.ui.post.feed.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gp.socialapp.presentation.post.feed.components.icons.FeedIcons
import com.gp.socialapp.presentation.post.feed.components.icons.feedicons.Attachment2
import com.gp.socialapp.presentation.post.feed.components.icons.feedicons.Comment
import com.gp.socialapp.presentation.post.feed.components.icons.feedicons.Dislikefilled
import com.gp.socialapp.presentation.post.feed.components.icons.feedicons.Dislikeoutlined
import com.gp.socialapp.presentation.post.feed.components.icons.feedicons.Likefilled
import com.gp.socialapp.presentation.post.feed.components.icons.feedicons.Likeoutlined

@Composable
fun BottomRow(
    upVotes: List<String>,
    downVotes: List<String>,
    commentCount: Int,
    votes: Int,
    onUpVoteClicked: () -> Unit,
    onDownVoteClicked: () -> Unit,
    onCommentClicked: () -> Unit,
    currentUserID: String,
    filesCount: Int = 0,
    onShowFilesClicked: () -> Unit,
    onShareClicked: () -> Unit,
) {
    val isUpvoted = upVotes.contains(currentUserID)
    val isDownvoted = downVotes.contains(currentUserID)
    val upvoteColor = MaterialTheme.colorScheme.onPrimaryContainer
    val neutralColor = MaterialTheme.colorScheme.secondaryContainer
    val downvoteColor = MaterialTheme.colorScheme.error
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding( top = 8.dp)
            .sizeIn(
                maxHeight = 35.dp
            ),
        horizontalArrangement = Arrangement.Start
    ) {
        FilledTonalButton(
            onClick = onUpVoteClicked,
            contentPadding = PaddingValues(6.dp),
            colors = ButtonDefaults.filledTonalButtonColors(
                containerColor = if (isUpvoted) upvoteColor else if(isDownvoted) downvoteColor else neutralColor,
                contentColor = if (isUpvoted || isDownvoted) Color.White else upvoteColor
            ),
        ) {
            Icon(
                imageVector =if(isUpvoted) FeedIcons.Likefilled else FeedIcons.Likeoutlined,
                contentDescription = "UpVote",
                modifier = Modifier
                    .clickable {
                        onUpVoteClicked()
                    },
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = votes.toString(),
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                ),
                modifier = Modifier.sizeIn(
                    minWidth = 20.dp
                ),
                textAlign = TextAlign.Center,
            )
            Spacer(modifier = Modifier.width(6.dp))
            Icon(
                imageVector = if(isDownvoted) FeedIcons.Dislikefilled else FeedIcons.Dislikeoutlined,
                contentDescription = "DownVote",
                modifier = Modifier
                    .clickable {
                        onDownVoteClicked()
                    }
            )


        }
        Spacer(modifier = Modifier.width(16.dp))
        FilledTonalButton(
            onClick = onCommentClicked,
            contentPadding = PaddingValues(
                horizontal = 12.dp,
            )
            ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    imageVector = FeedIcons.Comment,
                    contentDescription = "Comment",
                    tint = MaterialTheme.colorScheme.onPrimaryContainer,
                    modifier = Modifier.padding(horizontal = 8.dp).size(20.dp)
                )
                Text(
                    text = commentCount.toString(),
                    style = TextStyle(
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    ),
                    modifier = Modifier.padding(end = 8.dp)
                )
            }
        }
        if (filesCount > 0) {
            Spacer(modifier = Modifier.width(16.dp))
            FilledTonalButton(
                onClick = onShowFilesClicked,
                contentPadding = PaddingValues(
                    horizontal = 12.dp,
                )
                ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Icon(
                        imageVector = FeedIcons.Attachment2,
                        contentDescription = "Attachments",
                        tint = MaterialTheme.colorScheme.onPrimaryContainer,
                        modifier = Modifier.padding(horizontal = 8.dp).size(24.dp)
                    )
                    Text(
                        text = filesCount.toString(),
                        style = TextStyle(
                            color = MaterialTheme.colorScheme.onPrimaryContainer,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        ),
                        modifier = Modifier.padding(end = 8.dp)
                    )
                }
            }
        }
        Spacer(modifier = Modifier.weight(1f))
        FilledTonalButton(
            onClick = onShareClicked,
            enabled = false,
            ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    imageVector = Icons.Default.Share,
                    contentDescription = "Share Post",
                    tint = MaterialTheme.colorScheme.onPrimaryContainer,
                )
            }
        }
    }
}