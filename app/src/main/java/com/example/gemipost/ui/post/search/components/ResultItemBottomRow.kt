package com.example.gemipost.ui.post.search.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
) {
    Column(
        horizontalAlignment = Alignment.Start,
        modifier = modifier.padding(vertical = 8.dp, horizontal = 16.dp),
    ) {
        HorizontalDivider(
            modifier = Modifier.height(1.dp).padding(bottom = 8.dp),
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Row(
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(
                text = "$voteCount ${if(voteCount == 1) "vote" else "votes"}",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
            Text(
                text = " · ",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )

            Text(
                text = "$replyCount ${if(replyCount == 1) "reply" else "replies"}",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }

    }
}