package com.example.gemipost.data.post.source.local

import com.example.gemipost.data.post.source.remote.model.Post
import com.example.gemipost.utils.PostResults
import com.gp.socialapp.util.Result
import kotlinx.coroutines.flow.Flow

interface PostLocalDataSource {
     fun updateDataBase(posts: List<Post>) : Result<Unit, PostResults>
    fun searchByTitle(title: String): Flow<List<Post>>
    fun searchByTag(tag: String): Flow<List<Post>>
    suspend fun addRecentSearch(search: String)
    suspend fun getRecentSearches(): List<String>
    suspend fun deleteRecentSearch(search: String)
}