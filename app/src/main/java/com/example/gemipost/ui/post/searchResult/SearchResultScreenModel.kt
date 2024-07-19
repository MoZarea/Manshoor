package com.gp.socialapp.presentation.post.searchResult

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.example.gemipost.data.post.repository.PostRepository
import com.example.gemipost.data.post.source.remote.model.Tag
import com.gp.socialapp.util.DispatcherIO
import com.gp.socialapp.util.Result
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SearchResultScreenModel(
    private val postRepo: PostRepository
) : ScreenModel {
    private val _uiState = MutableStateFlow(SearchResultUiState())
    val uiState = _uiState.asStateFlow()
    fun initScreenModel(searchTerm: String, searchTag: Tag, isTag: Boolean) {
        screenModelScope.launch(DispatcherIO) {
            if (isTag) {
                postRepo.searchByTag(searchTag).collect {
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