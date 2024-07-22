package com.example.gemipost.ui.post.feed.components

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.gemipost.data.post.source.remote.model.PostAttachment
import com.example.gemipost.ui.post.feed.PostEvent

@Composable
fun PostContent(
    title: String,
    body: String,
    attachments: List<PostAttachment>,
    onPostEvent: (PostEvent) -> Unit
) {
    BoxWithConstraints {
        val maxWidth = maxWidth
        Column(
            Modifier
                .fillMaxWidth()
        ) {
            PostTitle(title)
            PostBody(body)
            Attachments(
                attachments = attachments,
                onPostEvent = onPostEvent,
                width = maxWidth
            )
        }
    }
}