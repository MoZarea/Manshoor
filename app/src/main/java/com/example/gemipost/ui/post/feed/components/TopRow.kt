package com.example.gemipost.ui.post.feed.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Circle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.gemipost.ui.post.postDetails.components.CircularAvatar

@Composable
fun PostTopRow(
    imageUrl: String,
    userName: String,
    publishedAt: String,
    isAuthor: Boolean,
    onEditPostClicked: () -> Unit,
    onDeletePostClicked: () -> Unit,
    onReportPostClicked: () -> Unit,
    onUserClick: () -> Unit,
) {
    Row(
        modifier = Modifier.background(Color.Transparent),
        verticalAlignment = Alignment.CenterVertically
    ) {
        CircularAvatar(
            imageURL = imageUrl,
            size = 24.dp,
            placeHolderImageVector = Icons.Default.Circle,
            onClick = onUserClick
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            UserName(
                userName = userName,
                onClick = onUserClick,
            )
            PostDate(
                publishedAt = publishedAt,
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        OptionButton(
            onEditPostClicked = onEditPostClicked,
            onDeletePostClicked = onDeletePostClicked,
            onReportPostClicked = onReportPostClicked,
            isAuthor = isAuthor
        )
    }
}