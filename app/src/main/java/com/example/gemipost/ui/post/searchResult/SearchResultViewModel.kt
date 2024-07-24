package com.example.gemipost.ui.post.searchResult

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gemipost.data.post.repository.PostRepository
import com.example.gemipost.data.post.source.remote.model.Tag
import com.example.gemipost.ui.post.searchResult.SearchResultUiState

import com.gp.socialapp.util.Result
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
    fun init(searchTerm: String, searchTagIntColor: Int, isTag: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            if (isTag) {
                postRepo.searchByTag(searchTerm).collect {
                    when (it) {
                        is com.gp.socialapp.util.Result.Success -> {
                            val posts = it.data
                            _uiState.update { it.copy(posts = posts) }
                        }

                        is com.gp.socialapp.util.Result.Error -> {
                            // Handle error
                        }

                        Result.Loading -> Unit
                    }
                }
            } else {
                postRepo.searchByTitle(searchTerm).collectLatest {
                    when (it) {
                        is Result.Success -> {
                            val posts = it.data
                            _uiState.update { it.copy(posts = posts) }
                        }

                        is Result.Error -> {
                            // Handle error
                        }

                        Result.Loading -> Unit
                    }
                }
            }
        }
    }
}