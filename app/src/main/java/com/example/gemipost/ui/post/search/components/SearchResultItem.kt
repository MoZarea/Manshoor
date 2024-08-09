package com.example.gemipost.ui.post.search.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.gemipost.data.post.source.remote.model.Post
import com.example.gemipost.ui.theme.GemiPostTheme
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
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer /*MaterialTheme.colorScheme.surfaceVariant*/
        ),
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(horizontal = 8.dp)
        ) {
            ResultItemTopRow(
                imageUrl = item.authorPfp,
                userName = item.authorName,
                publishedAt = item.createdAt.getPostDate(),
                isAuthor = false,
                onEditPostClicked = {},
                onDeletePostClicked = {},
                onReportPostClicked = {},
                onUserClick = {
                    onPostAuthorClicked(item.authorID)
                }
            )
            Row {
                Column (modifier = Modifier.weight(0.65f)){
                    ResultItemContent(
                        title = item.title,
                        body = item.body,
                        images = item.attachments
                    )

                    ResultItemBottomRow(
                        voteCount = item.votes,
                        replyCount = item.replyCount,
                    )
                }
//                if (item.attachments.isNotEmpty()) {
//                    SearchResultItemImages(
//                        modifier = Modifier.weight(0.35f),
//                        imageUrl = item.attachments[0],
//                        onImageClick = { }
//                    )
//                }
            }

        }
    }
}

@Preview(name = "Light Mode")
@Preview(name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Preview(name = "Full Preview", showSystemUi = true)
@Composable
private fun SearchResultItemPreview() {
    GemiPostTheme {
        Surface(tonalElevation = 5.dp) {
            SearchResultItem(
                item = Post(
                    id = "1",
                    authorID = "1",
                    authorName = "Muhammed Edrees",
                    authorPfp = "",
                    title = "Test Title",
                    body = "Test Body",
                    createdAt = 1722046770866,
                    votes = 5,
                    replyCount = 1,
                    attachments = listOf("https://dfstudio-d420.kxcdn.com/wordpress/wp-content/uploads/2019/06/digital_camera_photo-980x653.jpg")
                ),
                onPostClicked = {},
                onPostAuthorClicked = {}
            )
        }
    }
}