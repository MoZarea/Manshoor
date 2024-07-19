package com.gp.socialapp.presentation.post.search.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun RecentSearchItem(
    modifier: Modifier = Modifier,
    item: String,
    onItemClick: (String) -> Unit,
    onDeleteItem: (String) -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .clickable { onItemClick(item) }
    ) {
        Text(
            text = item,
            style = MaterialTheme.typography.bodyMedium,
            maxLines = 1,
            modifier = Modifier.padding(horizontal = 8.dp).weight(1f).focusable(false)
        )
        IconButton(
            onClick = { onDeleteItem(item) }
        ) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = "Delete",
                )
        }
    }

}