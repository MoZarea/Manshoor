package com.example.gemipost.ui.post.feed.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp
import com.example.gemipost.data.post.source.remote.model.PostAttachment
import com.example.gemipost.ui.post.feed.PostEvent
import com.example.gemipost.utils.MimeType

@Composable
fun Attachments(
    attachments: List<PostAttachment>,
    width: Dp,
    onPostEvent: (PostEvent) -> Unit
) {
    val images = attachments.filter {
        MimeType.getMimeTypeFromFileName(it.name) is MimeType.Image
    }
    if(images.isNotEmpty()){
        ImagePager(
            pageCount = images.size,
            images = images,
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
}