package com.example.gemipost.ui.post.feed.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gemipost.data.post.source.remote.model.Post
import com.example.gemipost.ui.post.feed.PostEvent
import com.example.gemipost.ui.theme.GemiPostTheme
import com.example.gemipost.utils.MimeType
import com.example.gemipost.utils.LocalDateTimeUtil.getPostDate
import com.gp.socialapp.util.ModerationSafety

@Composable
fun FeedPostItem(
    post: Post, onPostEvent: (PostEvent) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Card(
            onClick = { onPostEvent(PostEvent.OnPostClicked(post)) },
            shape = RoundedCornerShape(4.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 2.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.onSecondary)
        ) {
            if (post.moderationStatus.isUnsafe()) {
                UnsafePostItem()
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(8.dp)
                ) {
                    PostTopRow(
                        imageUrl = post.authorPfp,
                        userName = post.authorName,
                        publishedAt = post.createdAt.getPostDate(),
                        onEditPostClicked = { onPostEvent(PostEvent.OnPostEdited(post)) },
                        onDeletePostClicked = { onPostEvent(PostEvent.OnPostDeleted(post)) },
                        onReportPostClicked = { onPostEvent(PostEvent.OnPostReported(post)) },
                        onUserClick = { onPostEvent(PostEvent.OnPostAuthorClicked(post.authorID)) },
                        isAuthor = true

                    )
                    TagsFlowRow(selectedTags = post.tags.toSet(),
                        onTagClicked = { onPostEvent(PostEvent.OnTagClicked(it)) })
                    PostContent(
                        title = post.title,
                        body = post.body,
                        attachments = post.attachments,
                        onPostEvent = onPostEvent
                    )

                    BottomRow(upVotes = post.upvoted,
                        downVotes = post.downvoted,
                        commentCount = post.replyCount,
                        votes = post.votes,
                        onUpVoteClicked = { onPostEvent(PostEvent.OnPostUpVoted(post)) },
                        onDownVoteClicked = { onPostEvent(PostEvent.OnPostDownVoted(post)) },
                        onCommentClicked = {
                            onPostEvent(PostEvent.OnCommentClicked(post))
                        },
                        currentUserID = "currentUserID",
                        onShareClicked = { onPostEvent(PostEvent.OnPostShareClicked(post)) }
                    )
                }
            }
        }

    }
}

@Composable
fun ColumnScope.UnsafePostItem() {
    Text(
        text = "This post has been flagged as unsafe and was deleted by ai content moderation.",
        style = MaterialTheme.typography.titleMedium.copy(fontSize = 20.sp),
        modifier = Modifier.padding(8.dp)
    )

    HorizontalDivider(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        thickness = 0.5.dp,
    )
}
fun String.isUnsafe(): Boolean {
    return this == ModerationSafety.UNSAFE_TITLE.name || this == ModerationSafety.UNSAFE_BODY.name || this == ModerationSafety.UNSAFE_IMAGE.name
}

@Composable
@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL)
fun PreviewFeedPostItem() {
    GemiPostTheme {


    FeedPostItem(
        post = Post(
            id = "1",
            title = "Title",
            body = "Body",
        )
    ) {}
}}