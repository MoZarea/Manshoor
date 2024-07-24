package com.example.gemipost.ui.post.feed.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp
import com.example.gemipost.ui.post.feed.PostEvent

@Composable
fun Attachments(
    attachments: List<String>,
    width: Dp,
    onPostEvent: (PostEvent) -> Unit
) {
    ImagePager(
        pageCount = attachments.size,
        images = attachments,
        width = width,
        onImageClicked = { selectedImage ->
            onPostEvent(
                PostEvent.OnImageClicked(
                    selectedImage
                )
            )
        },
    )
}
