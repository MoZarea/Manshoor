package com.gp.socialapp.presentation.post.search

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.example.gemipost.data.post.repository.PostRepository
import com.gp.socialapp.util.DispatcherIO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SearchScreenModel(
    private val postRepo: PostRepository
): ScreenModel {
    private val _uiState = MutableStateFlow(SearchUiState())
    val uiState = _uiState.asStateFlow()
    fun initScreenModel() {
        screenModelScope.launch(DispatcherIO) {
            val result = postRepo.getRecentSearches()
            _uiState.value = _uiState.value.copy(recentSearches = result)
        }
    }

    fun deleteRecentSearchItem(recentSearch: String) {
        screenModelScope.launch(DispatcherIO) {
            postRepo.deleteRecentSearch(recentSearch)
            val result = postRepo.getRecentSearches()
            _uiState.value = _uiState.value.copy(recentSearches = result)
        }
    }

    fun onSearchQueryChanged(query: String) {
//        TODO("Not yet implemented")
    }

    fun addRecentSearchItem(item: String) {
        screenModelScope.launch (DispatcherIO){
            if(_uiState.value.recentSearches.contains(item)){
                return@launch
            }
            postRepo.addRecentSearch(item)
        }
    }
}