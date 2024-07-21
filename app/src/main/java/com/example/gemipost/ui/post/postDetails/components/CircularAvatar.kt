package com.example.gemipost.ui.post.postDetails.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ErrorOutline
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun CircularAvatar(
    imageURL: String,
    size: Dp,
    modifier: Modifier = Modifier,
    placeHolderPainter: Painter,
    onClick: () -> Unit = {},
) {
    val avatarModifier = modifier
        .size(size)
        .clip(CircleShape)
        .clickable { onClick() }
    if (imageURL.isNotBlank()) {
        GlideImage(
            imageModel = { imageURL }, // loading a network image using an URL.
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
    } else {
        Icon(
            painter = placeHolderPainter,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.outline,
            modifier = avatarModifier
        )
    }
}

@Composable
fun CircularAvatar(
    modifier: Modifier = Modifier,
    imageURL: String,
    size: Dp,
    placeHolderImageVector: ImageVector,
    onClick: () -> Unit = {},
) {
    val avatarModifier = modifier
        .size(size)
        .clip(CircleShape)
        .clickable { onClick() }
    if (imageURL.isNotBlank()) {
        GlideImage(
            imageModel = { imageURL }, // loading a network image using an URL.
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
    } else {
        Icon(
            imageVector = placeHolderImageVector,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.outline,
            modifier = avatarModifier
        )
    }
}