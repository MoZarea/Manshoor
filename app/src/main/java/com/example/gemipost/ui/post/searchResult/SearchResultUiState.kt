package com.gp.socialapp.presentation.post.searchResult

import com.example.gemipost.data.post.source.remote.model.Post

data class SearchResultUiState(
    val posts: List<Post> = emptyList(),
)
