package com.example.gemipost.data.post.source.local

import com.example.gemipost.data.post.source.remote.model.Post
import kotlinx.coroutines.flow.Flow

interface PostLocalDataSource {
    suspend fun insertPost(post: Post)
    fun getAllPosts(): Flow<List<Post>>
    fun getPostById(id: String): Flow<Post>
    suspend fun deletePostById(id: String)
    suspend fun deleteAllPosts()
    fun searchByTitle(title: String): Flow<List<Post>>
    fun searchByTag(tag: String): Flow<List<Post>>
}