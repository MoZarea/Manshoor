package com.example.gemipost.data.post.repository

import com.example.gemipost.data.post.source.remote.model.Post
import com.example.gemipost.data.post.source.remote.model.Tag
import com.gp.socialapp.util.PostError
import com.gp.socialapp.util.Result
import kotlinx.coroutines.flow.Flow

interface PostRepository {
    suspend fun insertLocalPost(post: Post)
    fun getPosts(): Flow<Result<List<Post>, PostError>>
    suspend fun updatePost(post: Post): Result<Unit, PostError>
    suspend fun deletePost(post: Post): Result<Unit, PostError>
    suspend fun upvotePost(post: Post, userId: String): Result<Unit, PostError>
    suspend fun downvotePost(post: Post, userId: String): Result<Unit, PostError>
    suspend fun fetchPostById(id: String): Flow<Post>
    suspend fun createPost(post: Post): Flow<Result<Unit, PostError>>
    suspend fun reportPost(postId: String, reporterId: String): Result<Unit, PostError>
    fun searchByTitle(title: String): Flow<Result<List<Post>, PostError>>
    fun getAllTags(communityId: String): Flow<List<Tag>>
    suspend fun insertTag(tag: Tag)
    suspend fun getRecentSearches(): List<String>
    suspend fun deleteRecentSearch(search: String)
    suspend fun addRecentSearch(search: String)
    fun searchByTag(tag: Tag): Flow<Result<List<Post>, PostError>>
    suspend fun openAttachment(url: String, mimeType: String)
    suspend fun getUserPosts(userId: String): Result<List<Post>,PostError>
}