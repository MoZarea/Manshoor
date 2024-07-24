package com.example.gemipost.ui.post.edit

import androidx.lifecycle.ViewModel
import com.example.gemipost.data.post.repository.PostRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class EditPostScreenModel(
    private val postRepository: PostRepository
):ViewModel()  {
    val _uiState = MutableStateFlow(EditPostUIState())
    val uiState = _uiState.asStateFlow()

}