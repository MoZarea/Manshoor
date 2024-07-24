package com.example.gemipost.data.post.repository


import com.example.gemipost.data.post.source.remote.PostRemoteDataSource
import com.example.gemipost.data.post.source.remote.model.Post
import com.example.gemipost.data.post.source.remote.model.PostRequest
import com.gp.socialapp.util.PostError
import com.gp.socialapp.util.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.transformLatest


class PostRepositoryImpl(
    private val postRemoteSource: PostRemoteDataSource,

    ) : PostRepository {
    override suspend fun createPost(post: Post): Flow<Result<Unit, PostError>> {
        val request = PostRequest.CreateRequest(post)
        return postRemoteSource.createPost(request)
    }

    override suspend fun reportPost(
        postId: String,
        reporterId: String
    ): Result<Unit, PostError> {
        val request = PostRequest.ReportRequest(postId, reporterId)
        return postRemoteSource.reportPost(request)
    }

    override fun searchByTitle(title: String): Flow<Result<List<Post>, PostError>> =
        postRemoteSource.searchByTitle(title)


    override fun getPosts(): Flow<Result<List<Post>, PostError>> =
        postRemoteSource.fetchPosts()


    override suspend fun updatePost(post: Post): Result<Unit, PostError> {
        val request = PostRequest.UpdateRequest(post)
        return postRemoteSource.updatePost(request)
    }


    override suspend fun deletePost(post: Post): Result<Unit, PostError> =
        postRemoteSource.deletePost(PostRequest.DeleteRequest(post.id))

    override suspend fun upvotePost(post: Post, userId: String): Result<Unit, PostError>
        = postRemoteSource.upvotePost(PostRequest.UpvoteRequest(post.id, userId))

    override suspend fun downvotePost(
        post: Post,
        userId: String
    ): Result<Unit, PostError> =
        postRemoteSource.downvotePost(PostRequest.DownvoteRequest(post.id, userId))

    override suspend fun fetchPostById(id: String): Result<Post,PostError> =
        postRemoteSource.fetchPostById(id).first()



    override suspend fun getRecentSearches(): List<String> {
        return emptyList()
    }

    override suspend fun deleteRecentSearch(search: String) {

    }

    override suspend fun addRecentSearch(search: String) {

    }

    override fun searchByTag(tagLabel: String): Flow<Result<List<Post>, PostError>>
        = postRemoteSource.searchByTag(tagLabel)


}