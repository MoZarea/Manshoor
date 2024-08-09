package com.example.gemipost.ui.post.search.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

@Composable
fun SearchSuggestions(
    modifier: Modifier = Modifier,
    searchQuery: String,
    recentSearches: List<String>,
    onDeleteRecentItem: (String) -> Unit,
    onSearch: (String) -> Unit,
    suggestionItems: List<String>,
) {
    if (searchQuery.isBlank()) {
        RecentSearchesSection(
            modifier = modifier,
            items = recentSearches,
            onItemClick = onSearch,
            onDeleteItem = onDeleteRecentItem
        )
    } else {
        LazyColumn(
            modifier = modifier.padding(horizontal = 16.dp),
            contentPadding = PaddingValues(vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(suggestionItems.size) { index ->
                Text(
                    text = suggestionItems[index],
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier
                        .clickable { onSearch(suggestionItems[index]) }
                        .fillMaxWidth()
                        .padding(8.dp)
                        .animateItem()
                )
            }
        }
    }
}