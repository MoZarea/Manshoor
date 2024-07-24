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

    override fun searchByTitle(title: String): Flow<Result<List<Post>, PostError>> = flow {
        emit(Result.Loading)
        try {
            if (title.isEmpty()) {
                emit(Result.Success(emptyList()))
                return@flow
            } else {
//                postLocalSource.searchByTitle(title).collect {
//                    emit(Result.Success(it))
//                }
            }
        } catch (e: Exception) {
            emit(Result.Error(PostError.SERVER_ERROR))
        }
    }


    override fun getPosts(): Flow<Result<List<Post>, PostError>> =
        postRemoteSource.fetchPosts()


    override suspend fun updatePost(post: Post): Result<Unit, PostError> {
        val request = PostRequest.UpdateRequest(post)
        return postRemoteSource.updatePost(request)
    }


    override suspend fun deletePost(post: Post): Result<Unit, PostError> {
        val request = PostRequest.DeleteRequest(post.id)
//        postLocalSource.deletePostById(post.id)
        return postRemoteSource.deletePost(request)
    }

    override suspend fun upvotePost(post: Post, userId: String): Result<Unit, PostError> {
        val request = PostRequest.UpvoteRequest(
            post.id,
            userId
        )
        return postRemoteSource.upvotePost(request)
    }

    override suspend fun downvotePost(
        post: Post,
        userId: String
    ): Result<Unit, PostError> {
        val request = PostRequest.DownvoteRequest(
            post.id,
            userId
        )
        return postRemoteSource.downvotePost(request)
    }

    override suspend fun fetchPostById(id: String): Result<Post,PostError> =
        postRemoteSource.fetchPostById(id).first()

    override suspend fun getRecentSearches(): List<String> {
        return emptyList()
    }

    override suspend fun deleteRecentSearch(search: String) {

    }

    override suspend fun addRecentSearch(search: String) {

    }

    override fun searchByTag(tagLabel: String): Flow<Result<List<Post>, PostError>> = flow {
        emit(Result.Loading)
        try {
            if (tagLabel.isEmpty()) {
                emit(Result.Success(emptyList()))
                return@flow
            } else {
//                postLocalSource.searchByTag(tag.label).collect {
//                    emit(Result.Success(it))
//                }
            }
        } catch (e: Exception) {
            emit(Result.Error(PostError.SERVER_ERROR))
        }
    }


}