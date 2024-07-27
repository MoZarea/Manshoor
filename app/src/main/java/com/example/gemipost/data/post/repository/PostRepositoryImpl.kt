package com.example.gemipost.data.post.repository


import android.graphics.Bitmap
import com.example.gemipost.data.post.source.local.PostLocalDataSource
import com.example.gemipost.data.post.source.remote.PostRemoteDataSource
import com.example.gemipost.data.post.source.remote.model.Post
import com.example.gemipost.data.post.source.remote.model.PostRequest
import com.example.gemipost.utils.PostResults
import com.gp.socialapp.util.Result
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch


class PostRepositoryImpl(
    private val postRemoteSource: PostRemoteDataSource,
    private val postLocalSource: PostLocalDataSource

) : PostRepository {
    override suspend fun createPost(post: Post): Flow<Result<PostResults, PostResults>> {
        val request = PostRequest.CreateRequest(post)
        return postRemoteSource.createPost(request)
    }

    override suspend fun reportPost(
        postId: String,
        title: String,
        body: String,
        images: List<Bitmap>
    ): Result<PostResults, PostResults> {
        return postRemoteSource.reportPost(postId, title, body, images)
    }

    override fun searchByTitle(title: String): Flow<List<Post>> =
        postLocalSource.searchByTitle(title)


    @OptIn(DelicateCoroutinesApi::class)
    override fun getPosts(): Flow<Result<List<Post>, PostResults>> {
        val posts = postRemoteSource.fetchPosts()
        GlobalScope.launch(Dispatchers.Default) {
            posts.collect { result ->
                if (result is Result.Success) {
                    postLocalSource.updateDataBase(result.data)
                }
            }
        }
        return posts

    }


    override suspend fun updatePost(post: Post): Result<PostResults, PostResults> {
        val request = PostRequest.UpdateRequest(post)
        return postRemoteSource.updatePost(request)
    }


    override suspend fun deletePost(post: Post): Result<PostResults, PostResults> =
        postRemoteSource.deletePost(PostRequest.DeleteRequest(post.id))

    override suspend fun upvotePost(post: Post, userId: String): Result<Unit, PostResults> =
        postRemoteSource.upvotePost(PostRequest.UpvoteRequest(post.id, userId))

    override suspend fun downvotePost(
        post: Post,
        userId: String
    ): Result<Unit, PostResults> =
        postRemoteSource.downvotePost(PostRequest.DownvoteRequest(post.id, userId))

    override suspend fun fetchPostById(id: String): Result<Post, PostResults> =
        postRemoteSource.fetchPostById(id).first()


    override suspend fun getRecentSearches(): List<String> =
        postLocalSource.getRecentSearches()

    override suspend fun deleteRecentSearch(search: String) =
        postLocalSource.deleteRecentSearch(search)

    override suspend fun addRecentSearch(search: String) {
        postLocalSource.addRecentSearch(search)

    }

    override fun searchByTag(tagLabel: String): Flow<List<Post>> =
        postLocalSource.searchByTag(tagLabel)


}