package com.example.gemipost.ui.auth.signup.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.rounded.Create
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

@Composable
fun SignUpAvatarSection(avatarByteArray: ByteArray, onChangeAvatarClicked: () -> Unit) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxWidth()
    ) {
        val imageModifier = Modifier
            .size(100.dp)
            .align(Alignment.Center)
            .clip(CircleShape)
        if (avatarByteArray.isEmpty()) {
            Icon(
                imageVector = Icons.Filled.AccountCircle,
                contentDescription = null,
                modifier = imageModifier,
                tint = MaterialTheme.colorScheme.outline
            )
        } else {
            //TODO
//                    val bitmap = avatarByteArray.toImageBitmap()
//                    Image(
//                        bitmap = bitmap,
//                        contentDescription = null,
//                        modifier = imageModifier,
//                        contentScale = ContentScale.Crop
//                    )
        }
        IconButton(
            onClick = { onChangeAvatarClicked() },
            modifier = Modifier
                .offset(x = 38.dp, y = 38.dp)
                .background(MaterialTheme.colorScheme.secondaryContainer, CircleShape)
                .size(32.dp)
        ) {
            Icon(
                imageVector = Icons.Rounded.Create,
                contentDescription = null,
            )
        }
    }
}
