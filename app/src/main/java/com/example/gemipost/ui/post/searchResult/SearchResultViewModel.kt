package com.example.gemipost.ui.post.searchResult

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gemipost.data.post.repository.PostRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SearchResultViewModel(
    private val postRepo: PostRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(SearchResultUiState())
    val uiState = _uiState.asStateFlow()
    fun init(searchTerm: String, isTag: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            if (isTag) {
                postRepo.searchByTag(searchTerm).collect {data->
                    println("searchByTag: $data")
                    _uiState.update { it.copy(posts = data) }

                }
            } else {
                postRepo.searchByTitle(searchTerm).collectLatest { data ->
                    _uiState.update { it.copy(posts = data) }
                }
            }
        }
    }
}