package com.example.gemipost.ui.post.feed

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gemipost.data.auth.repository.AuthenticationRepository
import com.example.gemipost.data.post.repository.PostRepository
import com.example.gemipost.data.post.source.remote.model.Post
import com.example.gemipost.utils.urlToBitmap
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
    val userid = "123456789"

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
                        _state.update { it.copy(posts = data.sortedByDescending { it.createdAt }) }
                    }

            }
        }
    }

    fun handleEvent(postEvent: PostEvent) {
        when(postEvent) {
            is PostEvent.OnPostDeleted -> deletePost(postEvent.post)
            is PostEvent.OnPostDownVoted -> downVotePost(postEvent.post)
            is PostEvent.OnPostReported ->  reportPost(postEvent.post, postEvent.context)
            is PostEvent.OnPostShareClicked -> TODO()
            is PostEvent.OnPostUpVoted ->  upvotedPost(postEvent.post)
            else->Unit
        }
    }
    private fun reportPost(post: Post, context: Context){
        viewModelScope.launch(Dispatchers.IO) {
            val images = mutableListOf<Bitmap>().apply{
                post.attachments.forEach { url->
                    urlToBitmap(
                        scope = this@launch,
                        imageURL = url,
                        context = context,
                        onSuccess = { bitmap ->
                            add(bitmap)
                        },
                        onError = { Log.d("seerde", "Error loading image") }
                    )
                }
            }
            postRepo.reportPost(post.id, post.title, post.body, images).onSuccess {
                Log.d("seerde", "Post reported")
            }.onFailure {
                Log.d("seerde", "Post not reported")
            }
        }
    }
    private fun upvotedPost(post: Post) {
        viewModelScope.launch(Dispatchers.IO) {
            postRepo.upvotePost(post, userid).onSuccess {
                println("zarea:Post upvoted")
            }.onFailure {
                println("zarea:Post not upvoted")
            }
        }
    }

    private fun downVotePost(post: Post) {
        viewModelScope.launch(Dispatchers.IO) {
            postRepo.downvotePost(post, userid).onSuccess {
                println("zarea:Post downvoted")
            }.onFailure {
                println("zarea:Post not downvoted")
            }
        }
    }

    private fun deletePost(post: Post) {
        viewModelScope.launch(Dispatchers.IO) {
            postRepo.deletePost(post).onSuccess {
                println("zarea:Post deleted")
            }.onFailure {
                println("zarea:Post not deleted")
            }
        }
    }

}