package com.example.gemipost.ui.post.search

data class SearchUiState(
    val recentSearches: List<String> = emptyList(),
    val suggestionItems: List<String> = emptyList()
)
