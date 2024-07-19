package com.gp.socialapp.presentation.post.create.component

import androidx.compose.foundation.border
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun MyIconButton(
    onClick: () -> Unit,
    icon: ImageVector
) {
    IconButton(
        onClick = {
            onClick()
        },
        modifier = Modifier.border(
            width = 1.dp,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f),
            shape = CircleShape
        )
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null
        )
    }
}