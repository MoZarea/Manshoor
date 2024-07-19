package com.gp.socialapp.presentation.post.searchResult.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ResultItemBottomRow(
    modifier: Modifier = Modifier,
    voteCount: Int,
    replyCount: Int,
    attachmentCount: Int,
) {
    Column(
        horizontalAlignment = Alignment.Start,
        modifier = modifier.padding(vertical = 8.dp, horizontal = 16.dp),
    ) {
        HorizontalDivider(
            modifier = Modifier.height(2.dp).padding(bottom = 8.dp),
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f),
        )
        Text(
            text = "$voteCount votes - $replyCount replies - $attachmentCount attachments",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.outline,
        )
    }
}