package com.example.gemipost.ui.post.search

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gemipost.data.post.repository.PostRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SearchViewModel(
    private val postRepo: PostRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(SearchUiState())
    val uiState = _uiState.asStateFlow()
    fun init(isTag: Boolean, searchTerm: String) {
        viewModelScope.launch(Dispatchers.IO) {
            if (isTag) {
                postRepo.searchByTag(searchTerm).collect { data ->
                    println("searchByTag: $data")
                    _uiState.update { it.copy(searchResult = data) }

                }
            } else {
                val result = postRepo.getRecentSearches()
                _uiState.value = _uiState.value.copy(recentSearches = result)
            }
        }
    }

    fun onSearch(searchTerm: String) {
        Log.d("SearchViewModel", "onSearch: $searchTerm")
        viewModelScope.launch(Dispatchers.IO) {
            postRepo.searchByTitle(searchTerm).collectLatest { data ->
                _uiState.update { it.copy(searchResult = data) }
                Log.d("SearchViewModel", "onSearch: $data")
            }
        }
    }

    fun deleteRecentSearchItem(recentSearch: String) {
        viewModelScope.launch(Dispatchers.IO) {
            postRepo.deleteRecentSearch(recentSearch)
            val result = postRepo.getRecentSearches()
            _uiState.value = _uiState.value.copy(recentSearches = result)
        }
    }

    fun onSearchQueryChanged(query: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.update { it.copy(searchQuery = query)}
            postRepo.searchByTitle(query).collectLatest { data ->
                println("searchByTitle: $data")
                _uiState.update { it.copy(suggestionItems = data.map { it.title }) }
            }
        }
    }

    fun addRecentSearchItem(item: String) {
        viewModelScope.launch(Dispatchers.IO) {
            if (_uiState.value.recentSearches.contains(item)) {
                return@launch
            }
            postRepo.addRecentSearch(item)
        }
    }
}