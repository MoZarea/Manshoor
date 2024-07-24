package com.example.gemipost.data.post.source.remote

import com.example.gemipost.data.post.source.remote.model.Post
import com.example.gemipost.data.post.source.remote.model.PostRequest
import com.example.gemipost.data.post.source.remote.model.PostRequest.DeleteRequest
import com.example.gemipost.data.post.source.remote.model.PostRequest.DownvoteRequest
import com.example.gemipost.data.post.source.remote.model.PostRequest.FetchRequest
import com.example.gemipost.data.post.source.remote.model.PostRequest.UpvoteRequest
import com.example.gemipost.data.post.source.remote.model.Tag
import com.gp.socialapp.util.PostError
import com.gp.socialapp.util.Result
import kotlinx.coroutines.flow.Flow

interface PostRemoteDataSource {
    fun createPost(request: PostRequest.CreateRequest): Flow<Result<Unit, PostError>>
    fun fetchPosts(request: FetchRequest): Flow<Result<List<Post>,PostError>>
    suspend fun updatePost(request: PostRequest.UpdateRequest): Result<Unit, PostError>
    suspend fun deletePost(request: DeleteRequest): Result<Unit, PostError>
    suspend fun upvotePost(request: UpvoteRequest): Result<Unit, PostError>
    suspend fun downvotePost(request: DownvoteRequest): Result<Unit, PostError>
    suspend fun reportPost(request: PostRequest.ReportRequest): Result<Unit, PostError>

    fun getAllTags(communityId: String): Flow<List<Tag>>
    suspend fun insertTag(tag: Tag)
    fun fetchAllPosts(): Flow<Result<List<Post>,PostError>>
    fun searchByTitle(title: String): Flow<Result<List<Post>, PostError>>
    fun searchByTag(tag: String): Flow<Result<List<Post>, PostError>>
    suspend fun getUserPosts(userId: String): Result<List<Post>,PostError>
    suspend fun getPostWithId(postId: String): Result<Post, PostError>
}