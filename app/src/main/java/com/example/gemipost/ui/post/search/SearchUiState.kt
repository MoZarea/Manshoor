package com.example.gemipost.ui.post.search

import com.example.gemipost.data.post.source.remote.model.Post

data class SearchUiState(
    val searchQuery: String = "",
    val searchResult: List<Post> = emptyList(),
    val recentSearches: List<String> = emptyList(),
    val suggestionItems: List<String> = emptyList()
)
