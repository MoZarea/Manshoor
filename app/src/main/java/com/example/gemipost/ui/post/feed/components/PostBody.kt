package com.example.gemipost.ui.post.feed.components

import androidx.compose.runtime.Composable

@Composable
fun PostBody(body: String) {
    if (body.isEmpty()) return
    ExpandableText(
        text = body,
        maxLinesCollapsed = 3,
    )
}