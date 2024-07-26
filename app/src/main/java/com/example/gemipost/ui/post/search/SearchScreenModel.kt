package com.example.gemipost.ui.post.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gemipost.data.post.repository.PostRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SearchViewModel(
    private val postRepo: PostRepository
): ViewModel() {
    private val _uiState = MutableStateFlow(SearchUiState())
    val uiState = _uiState.asStateFlow()
    fun init() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = postRepo.getRecentSearches()
            _uiState.value = _uiState.value.copy(recentSearches = result)
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
//        TODO("Not yet implemented")
    }

    fun addRecentSearchItem(item: String) {
        viewModelScope.launch (Dispatchers.IO){
            if(_uiState.value.recentSearches.contains(item)){
                return@launch
            }
            postRepo.addRecentSearch(item)
        }
    }
}