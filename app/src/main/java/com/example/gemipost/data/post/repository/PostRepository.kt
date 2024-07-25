package com.example.gemipost.data.post.repository

import com.example.gemipost.data.post.source.remote.model.Post
import com.example.gemipost.data.post.source.remote.model.Tag
import com.gp.socialapp.util.PostError
import com.gp.socialapp.util.Result
import kotlinx.coroutines.flow.Flow

interface PostRepository {
    fun getPosts(): Flow<Result<List<Post>, PostError>>
    suspend fun updatePost(post: Post): Result<Unit, PostError>
    suspend fun deletePost(post: Post): Result<Unit, PostError>
    suspend fun upvotePost(post: Post, userId: String): Result<Unit, PostError>
    suspend fun downvotePost(post: Post, userId: String): Result<Unit, PostError>
    suspend fun  fetchPostById(id: String): Result<Post,PostError>
    suspend fun createPost(post: Post): Flow<Result<Unit, PostError>>
    suspend fun reportPost(postId: String, title: String, body: String, attachments: List<String>): Result<Unit, PostError>
    fun searchByTitle(title: String): Flow<Result<List<Post>, PostError>>
    suspend fun getRecentSearches(): List<String>
    suspend fun deleteRecentSearch(search: String)
    suspend fun addRecentSearch(search: String)
    fun searchByTag(tagLabel: String): Flow<Result<List<Post>, PostError>>
}