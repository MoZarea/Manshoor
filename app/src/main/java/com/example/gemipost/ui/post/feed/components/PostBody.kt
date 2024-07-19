package com.gp.socialapp.presentation.post.feed.components

import androidx.compose.runtime.Composable

@Composable
fun PostBody(body: String) {
    if (body.isEmpty()) return
    ExpandableText(
        text = body,
        maxLinesCollapsed = 3,
    )
}