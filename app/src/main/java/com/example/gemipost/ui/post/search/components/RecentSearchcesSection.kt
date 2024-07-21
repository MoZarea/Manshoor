package com.example.gemipost.ui.post.search.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun RecentSearchesSection(
    modifier: Modifier = Modifier,
    items: List<String>,
    onItemClick: (String) -> Unit,
    onDeleteItem: (String) -> Unit,
) {
    if(items.isNotEmpty()){
        Column(modifier = modifier) {
            Text(
                text = "Recent Searches",
                style = MaterialTheme.typography.titleLarge
            )
            items.forEach { item ->
                RecentSearchItem(
                    item = item,
                    onItemClick = onItemClick,
                    onDeleteItem = onDeleteItem
                )
                HorizontalDivider()
            }
        }

    }
}