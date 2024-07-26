package com.example.gemipost.data.post.source.remote

import com.example.gemipost.data.post.source.remote.model.Post
import com.example.gemipost.data.post.source.remote.model.PostRequest
import com.example.gemipost.data.post.source.remote.model.PostRequest.DeleteRequest
import com.example.gemipost.data.post.source.remote.model.PostRequest.DownvoteRequest
import com.example.gemipost.data.post.source.remote.model.PostRequest.UpvoteRequest
import com.example.gemipost.utils.PostResults
import com.gp.socialapp.util.Result
import kotlinx.coroutines.flow.Flow

interface PostRemoteDataSource {
    fun createPost(request: PostRequest.CreateRequest): Flow<Result<PostResults, PostResults>>
    fun fetchPosts(): Flow<Result<List<Post>, PostResults>>
    fun fetchPostById(id: String): Flow<Result<Post, PostResults>>
    suspend fun updatePost(request: PostRequest.UpdateRequest): Result<PostResults, PostResults>
    suspend fun deletePost(request: DeleteRequest): Result<PostResults, PostResults>
    suspend fun upvotePost(request: UpvoteRequest): Result<Unit, PostResults>
    suspend fun downvotePost(request: DownvoteRequest): Result<Unit, PostResults>
    suspend fun reportPost(request: PostRequest.ReportRequest): Result<PostResults, PostResults>
    fun searchByTitle(title: String): Flow<Result<List<Post>, PostResults>>
    fun searchByTag(tag: String): Flow<Result<List<Post>, PostResults>>
}