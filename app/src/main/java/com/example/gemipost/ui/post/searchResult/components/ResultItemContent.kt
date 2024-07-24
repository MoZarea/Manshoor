package com.example.gemipost.ui.post.searchResult.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.gemipost.data.post.source.remote.model.PostAttachment

@Composable
fun ResultItemContent(
    modifier: Modifier = Modifier,
    title: String,
    body: String,
    attachments: List<String>,
    onImageClicked:(String) -> Unit = {}
) {
    Row (
        modifier = modifier.fillMaxWidth().padding(horizontal = 16.dp)
    ){
        Column{
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(vertical = 2.dp)
            )
            Text(
                text = body,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
            )
        }
        Spacer(modifier = Modifier.weight(1f))
//        if((attachments.firstOrNull()?.type) == FilePickerFileType.ImageContentType){
//            val imageURL = BASE_URL+attachments.first().url
//            Box(
//                modifier = Modifier
//                    .size(100.dp)
//                    .padding(horizontal = 16.dp)
//                    .clickable {
//                        onImageClicked(imageURL)
//                    }
//                    .clip(
//                        RoundedCornerShape(16.dp)
//                    ).background(MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.6f)),
//                contentAlignment = Alignment.CenterEnd
//            ) {
//                AutoSizeBox(imageURL) { action ->
//                    when (action) {
//                        is ImageAction.Success -> {
//                            Image(
//                                rememberImageSuccessPainter(action),
//                                contentDescription = null,
//                                modifier = Modifier
//                                    .align(Alignment.Center).fillMaxSize(),
//                                contentScale = ContentScale.FillHeight
//                            )
//                        }
//
//                        is ImageAction.Loading -> {
//                            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
//                        }
//
//                        is ImageAction.Failure -> {
//                            Icon(
//                                imageVector = FontAwesomeIcons.Solid.ExclamationTriangle,
//                                contentDescription = null,
//                                modifier = Modifier.size(36.dp).align(Alignment.Center),
//                            )
//                        }
//                    }
//                }
//            }
//        }
    }
}