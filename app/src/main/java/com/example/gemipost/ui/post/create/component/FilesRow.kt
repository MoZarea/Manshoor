package com.example.gemipost.ui.post.create.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.gemipost.data.post.source.remote.model.PostAttachment
import com.example.gemipost.utils.MimeType
import com.example.gemipost.utils.getFileImageVector

@Composable
fun FilesRow(
    modifier: Modifier = Modifier,
    files: List<PostAttachment>,
    onFileDelete: (PostAttachment) -> Unit,
    onAddFile: () -> Unit,
) {
    LazyRow(
        modifier = modifier.fillMaxWidth(),
    ) {
        item {
            AddNewFileButton(Modifier.padding(horizontal = 8.dp), onClick = onAddFile)

        }
        items(files) { postFile ->

            Box(

                modifier = Modifier
                    .size(64.dp)
                    .drawBehind {
                        drawRoundRect(
                            color = Color.Unspecified,
                            style = androidx.compose.ui.graphics.drawscope.Fill,
                            cornerRadius = CornerRadius(8.dp.toPx())
                        )
                    }
                    .clip(RoundedCornerShape(8.dp))
                    .clickable { onFileDelete(postFile) }.padding(horizontal = 2.dp)) {
                val mimeType = MimeType.getMimeTypeFromFileName(postFile.name)
                if (postFile.file.isEmpty()) {
                    when (mimeType) {
//                        is MimeType.Image -> Image(
//                            painter = rememberImagePainter(BASE_URL + postFile.url),
//                            contentDescription = null,
//                            contentScale = androidx.compose.ui.layout.ContentScale.FillHeight,
//                            modifier = Modifier.fillMaxSize()
//                        )

                        else -> Icon(
                            tint = Color.Unspecified,
                            imageVector = getFileImageVector(mimeType),
                            contentDescription = null,
                            modifier = Modifier.fillMaxSize(0.5f).align(Alignment.Center)
                        )
                    }
                } else if (postFile.file.isNotEmpty() && (mimeType is MimeType.Image)) {
//                    Image(
//                        modifier = Modifier.fillMaxSize().align(Alignment.Center),
//                        bitmap = postFile.file,
//                        contentDescription = null,
//                        contentScale = androidx.compose.ui.layout.ContentScale.FillHeight
//
//                    )
                } else {
                    Icon(
                        tint = Color.Unspecified,
                        imageVector = getFileImageVector(mimeType),
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize(0.5f).align(Alignment.Center)
                    )
                }

            }
        }

    }
}