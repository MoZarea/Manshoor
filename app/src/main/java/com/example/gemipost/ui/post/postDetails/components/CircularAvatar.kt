package com.example.gemipost.ui.post.postDetails.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp

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
//        AutoSizeBox(imageURL) { action ->
//            when (action) {
//                is ImageAction.Success -> {
//                    Image(
//                        rememberImageSuccessPainter(action),
//                        contentDescription = null,
//                        modifier = avatarModifier,
//                    )
//                }
//
//                is ImageAction.Loading -> {
//                    CircularProgressIndicator(modifier = avatarModifier)
//                }
//
//                is ImageAction.Failure -> {
//                    Icon(
//                        imageVector = Icons.Filled.Error,
//                        contentDescription = null,
//                        modifier = avatarModifier,
//                    )
//                }
//
//                else -> Unit
//            }
//        }
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
//        AutoSizeBox(imageURL) { action ->
//            when (action) {
//                is ImageAction.Success -> {
//                    Image(
//                        rememberImageSuccessPainter(action),
//                        contentDescription = null,
//                        contentScale = ContentScale.Crop,
//                        modifier = avatarModifier,
//                    )
//                }
//
//                is ImageAction.Loading -> {
//                    CircularProgressIndicator(modifier = avatarModifier)
//                }
//
//                is ImageAction.Failure -> {
//                    Icon(
//                        imageVector = Icons.Filled.Error,
//                        contentDescription = null,
//                        modifier = avatarModifier,
//                    )
//                }
//
//                else -> Unit
//            }
//        }
    } else {
        Icon(
            imageVector = placeHolderImageVector,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.outline,
            modifier = avatarModifier
        )
    }
}