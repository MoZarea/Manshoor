package com.example.gemipost.data.post.repository

import android.graphics.Bitmap
import com.example.gemipost.data.post.source.remote.model.Post
import com.example.gemipost.utils.PostResults
import com.gp.socialapp.util.Result
import kotlinx.coroutines.flow.Flow

interface PostRepository {
    fun getPosts(): Flow<Result<List<Post>, PostResults>>
    suspend fun updatePost(post: Post): Result<PostResults, PostResults>
    suspend fun deletePost(post: Post): Result<PostResults, PostResults>
    suspend fun upvotePost(post: Post, userId: String): Result<Unit, PostResults>
    suspend fun downvotePost(post: Post, userId: String): Result<Unit, PostResults>
    suspend fun  fetchPostById(id: String): Result<Post, PostResults>
    suspend fun createPost(post: Post): Flow<Result<PostResults, PostResults>>
    suspend fun reportPost(postId: String, title: String, body: String, images: List<Bitmap>): Result<PostResults, PostResults>
    fun searchByTitle(title: String): Flow<Result<List<Post>, PostResults>>
    suspend fun getRecentSearches(): List<String>
    suspend fun deleteRecentSearch(search: String)
    suspend fun addRecentSearch(search: String)
    fun searchByTag(tagLabel: String): Flow<Result<List<Post>, PostResults>>
}