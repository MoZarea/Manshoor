package com.example.gemipost.ui.post.postDetails.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gemipost.data.post.source.remote.model.NestedReply

@Composable
 fun ReplyAuthorName(authorName: String) {
    Text(text = authorName.run {
        if (this.length > 10) this.substring(
            0, 10
        ) else this
    } ?: " ",
        modifier = Modifier.padding(
            start = 8.dp, end = 4.dp
        ),
        overflow = if (authorName.length > 10) TextOverflow.Ellipsis else TextOverflow.Clip,
        fontSize = 12.sp,
        color = MaterialTheme.colorScheme.onPrimaryContainer)
}