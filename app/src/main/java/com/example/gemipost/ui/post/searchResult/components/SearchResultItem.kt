package com.example.gemipost.ui.post.searchResult.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.gemipost.data.post.source.remote.model.Post
import com.example.gemipost.ui.post.feed.components.PostTopRow
import com.example.gemipost.utils.LocalDateTimeUtil.getPostDate

@Composable
fun SearchResultItem(
    modifier: Modifier = Modifier,
    item: Post,
    onPostClicked: (String) -> Unit,
    onPostAuthorClicked: (String) -> Unit
) {
    Card(
        onClick = { onPostClicked(item.id) },
        shape = RoundedCornerShape(4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.03f)),
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            PostTopRow(
                imageUrl = item.authorPfp,
                userName = item.authorName,
                publishedAt = item.createdAt.getPostDate(),
                isAuthor = false,
                onEditPostClicked = {},
                onDeletePostClicked = {},
                onReportPostClicked = {},
                onUserClick = { onPostAuthorClicked(item.authorID)
                }
            )
            ResultItemContent(
                title = item.title,
                body = item.body,
                attachments = item.attachments
            )
            ResultItemBottomRow(
                voteCount = item.votes,
                replyCount = item.replyCount,
                attachmentCount = item.attachments.size
            )
        }
    }


}