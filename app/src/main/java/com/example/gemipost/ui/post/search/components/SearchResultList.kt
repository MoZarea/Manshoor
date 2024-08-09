package com.example.gemipost.ui.post.search.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.gemipost.data.post.source.remote.model.Post

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SearchResultList(
    modifier: Modifier = Modifier,
    posts: List<Post>,
    onPostClicked: (String) -> Unit,
    onPostAuthorClicked: (String) -> Unit
) {
    val state = rememberLazyListState()
    LazyColumn(
        state = state,
//        verticalArrangement = Arrangement.spacedBy(4.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(vertical = 8.dp),
        modifier = modifier.fillMaxSize()
    ) {
        items(posts.size) { index ->
            SearchResultItem(
                modifier = Modifier.animateItem(),
                item = posts[index],
                onPostClicked = onPostClicked,
                onPostAuthorClicked = onPostAuthorClicked
            )
        }
    }
}