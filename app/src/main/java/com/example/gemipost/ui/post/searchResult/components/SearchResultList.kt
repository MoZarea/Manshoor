package com.example.gemipost.ui.post.searchResult.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.gemipost.data.post.source.remote.model.Post
import com.example.gemipost.ui.post.searchResult.components.SearchResultItem

@Composable
fun SearchResultList(
    modifier: Modifier = Modifier,
    posts: List<Post>,
    onPostClicked: (String) -> Unit,
    onPostAuthorClicked: (String) -> Unit
) {
    LazyColumn(
        contentPadding = PaddingValues(8.dp),
        modifier = modifier.fillMaxSize()
    ) {
        items(posts.size) { index ->
            SearchResultItem(
                item = posts[index],
                onPostClicked = onPostClicked,
                onPostAuthorClicked = onPostAuthorClicked
            )
        }
    }
}