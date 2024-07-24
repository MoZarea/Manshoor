package com.example.gemipost.ui.post.feed

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gemipost.data.auth.repository.AuthenticationRepository
import com.example.gemipost.data.post.repository.PostRepository
import kotlinx.coroutines.Dispatchers

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class FeedScreenModel(
    private val postRepo: PostRepository,
    private val authRepo: AuthenticationRepository,
): ViewModel() {
    private val _state = MutableStateFlow(FeedUiState())
    val state = _state.asStateFlow()

    init {
        fetchPosts()
    }

    private fun fetchPosts() {
        viewModelScope.launch (Dispatchers.IO){
            postRepo.getPosts().collect {result->
                result.onLoading {
                    println("zarea:Loading")
                }
                    .onFailure {
                        println("zarea:Error")
                    }
                    .onSuccessWithData { data->
                        println("zarea:Success")
                        _state.update { it.copy(posts = data) }
                    }

            }
        }
    }

    fun handleEvent(postEvent: PostEvent) {
        when(postEvent) {
            is PostEvent.OnPostClicked -> {
                println("Post Clicked")
            }
            is PostEvent.OnPostDeleted -> TODO()
            is PostEvent.OnPostDownVoted -> TODO()
            is PostEvent.OnPostReported -> TODO()
            is PostEvent.OnPostShareClicked -> TODO()
            is PostEvent.OnPostUpVoted -> TODO()
            is PostEvent.OnTagClicked -> TODO()
            is PostEvent.OnViewFilesAttachmentClicked -> TODO()
            else->Unit
        }
    }

}