package com.example.gemipost.ui.post.search.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ErrorOutline
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun ResultItemContent(
    modifier: Modifier = Modifier,
    title: String,
    body: String,
    images: List<String>,
    onImageClicked:(String) -> Unit = {}
) {
    Row (
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ){
        Column{
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
//                modifier = Modifier.padding(top = 6.dp, bottom = 4.dp),
            )
            Text(
                text = body,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
fun SearchResultItemImages(
    modifier: Modifier = Modifier,
    imageUrl: String,
    onImageClick: () -> Unit
) {
    val avatarModifier = modifier
        .heightIn(max=100.dp)
        .widthIn(max = 144.dp)
        .clip(MaterialTheme.shapes.small)
        .clickable { onImageClick() }
    GlideImage(
        imageModel = { imageUrl }, // loading a network image using an URL.
        imageOptions = ImageOptions(
            contentScale = ContentScale.Crop,
            alignment = Alignment.Center
        ),
        modifier = avatarModifier,
        loading = {
            CircularProgressIndicator(modifier = Modifier
                .size(24.dp)
                .align(Alignment.Center))
        },
        failure = {
            Icon(
                imageVector = Icons.Default.ErrorOutline,
                contentDescription =null,
                modifier = Modifier.size(24.dp).align(Alignment.Center))
        }
    )
}