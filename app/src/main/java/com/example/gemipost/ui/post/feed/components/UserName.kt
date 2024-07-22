package com.example.gemipost.ui.post.feed.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp

@Composable
fun UserName(
    userName: String,
    onClick: () -> Unit
) {
    Text(
        text = userName,
        modifier = Modifier
            .padding(
                start = 8.dp,
                end = 8.dp,
            ).clickable { onClick() },
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        softWrap = true,
        style = MaterialTheme.typography.titleMedium,
    )
}