package com.example.gemipost.ui.post.search.components

import com.example.gemipost.ui.post.feed.components.OptionButton
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.gemipost.ui.post.postDetails.components.CircularAvatar

@Composable
fun ResultItemTopRow(
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
        modifier = Modifier.background(Color.Transparent).padding(start = 16.dp, top = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        CircularAvatar(
            imageURL = imageUrl,
            size = 36.dp,
            placeHolderImageVector = Icons.Default.AccountCircle,
            onClick = onUserClick
        )
        Column(
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.padding(horizontal = 8.dp)
        ) {
            Text(
                text = userName,
                modifier = Modifier.clickable { onUserClick() },
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                softWrap = true,
                style = MaterialTheme.typography.labelMedium,
            )
            Text(
                text = publishedAt,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
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