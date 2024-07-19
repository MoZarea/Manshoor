package com.gp.socialapp.presentation.post.feed.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.gemipost.data.post.source.remote.model.PostAttachment
import com.gp.socialapp.presentation.chat.chatroom.components.toReadableSize
import com.gp.socialapp.presentation.material.utils.MimeType
import com.gp.socialapp.presentation.material.utils.getFileImageVector
import com.gp.socialapp.presentation.post.feed.PostEvent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilesBottomSheet(
    modifier: Modifier = Modifier,
    attachments: List<PostAttachment>,
    onDismiss: () -> Unit,
    state: SheetState,
    onPostEvent: (PostEvent) -> Unit = { },
) {

    ModalBottomSheet(
        modifier = modifier,
        onDismissRequest = onDismiss,
        sheetState = state
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(1),
            verticalArrangement = Arrangement.Center,
            horizontalArrangement = Arrangement.Center,
            contentPadding = PaddingValues(8.dp),
        ) {
            items(attachments) {
                val mimeType = MimeType.getMimeTypeFromFileName(it.name)
                ListItem(
                    tonalElevation = 4.dp,
                    modifier = Modifier.padding(4.dp).clip(RoundedCornerShape(8.dp)).clickable { onPostEvent(PostEvent.OnAttachmentClicked(it)) },
                    headlineContent = {
                        Text(
                            text =  it.name,
                            style = MaterialTheme.typography.titleMedium,
                            maxLines = 1,
                        )
                    },
                    leadingContent = {
                        Icon(
                            imageVector = getFileImageVector(mimeType),
                            contentDescription = null,
                            modifier = Modifier.size(48.dp),
                            tint = Color.Unspecified
                        )
                    },
                    supportingContent = {
                        Text(
                            text = it.size.toReadableSize()+" - " + it.name.substringAfterLast('.').uppercase(),
                            style = MaterialTheme.typography.bodySmall,
                            maxLines = 1,
                            color = MaterialTheme.colorScheme.outline
                        )
                    }
                )
            }
        }
    }
}