package com.gp.socialapp.presentation.post.feed.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun PostDate(
    publishedAt: String,
) {
    Text(
        text = publishedAt,
        modifier = Modifier
            .padding(
                start = 4.dp,
                end = 8.dp,
            ),
        color = Color.Gray,
        fontSize = 12.sp
    )
}