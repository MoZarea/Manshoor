package com.example.gemipost.ui.post.searchResult.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.gemipost.data.post.source.remote.model.Tag
import com.gp.socialapp.presentation.post.feed.components.TagItem

@Composable
fun SearchResultHeader(
    modifier: Modifier = Modifier,
    searchTerm: String,
    searchTag: Tag,
    isTag: Boolean
) {
    Row(
        modifier = modifier.fillMaxWidth()
    ){
        Text(
            text = "Search results for: ${if (isTag) "" else searchTerm}",
            maxLines = 1,
            style = MaterialTheme.typography.titleMedium,
        )
        if(isTag){
            TagItem(
                tag = searchTag,
                onTagClicked = {},
                modifier = Modifier.padding(start = 8.dp)
            )
        }
    }

}