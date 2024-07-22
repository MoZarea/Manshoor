package com.example.gemipost.ui.post.feed.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.sp

@Composable
fun PostTitle(title: String) {
    if (title.isEmpty()) return
    Text(
        text = title,
        style = MaterialTheme.typography.titleMedium.copy(fontSize = 20.sp),
    )
}