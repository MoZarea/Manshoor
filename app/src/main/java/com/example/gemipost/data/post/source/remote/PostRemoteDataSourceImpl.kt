package com.example.gemipost.data.post.source.remote

import com.example.gemipost.data.post.source.remote.model.Post
import com.example.gemipost.data.post.source.remote.model.PostRequest
import com.example.gemipost.data.post.source.remote.model.PostRequest.DeleteRequest
import com.example.gemipost.data.post.source.remote.model.PostRequest.DownvoteRequest
import com.example.gemipost.data.post.source.remote.model.PostRequest.FetchRequest
import com.example.gemipost.data.post.source.remote.model.PostRequest.UpvoteRequest
import com.example.gemipost.data.post.source.remote.model.Tag
import com.example.gemipost.data.post.util.endPoint
import com.example.gemipost.utils.AppConstants
import com.google.firebase.firestore.FirebaseFirestore
import com.gp.socialapp.util.PostError
import com.gp.socialapp.util.Result
import com.gp.socialapp.util.Result.Companion.success
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow

class PostRemoteDataSourceImpl(
    private val db: FirebaseFirestore
) : PostRemoteDataSource {
val     colRef = db.collection(AppConstants.DB_Constants.POSTS.name)
    override  fun createPost(request: PostRequest.CreateRequest): Flow<Result<Unit,PostError>> =
         callbackFlow {
            trySend(Result.Loading)
            try {
                val docRef = colRef.document()
                docRef.set(request.post.copy(id=docRef.id))
                docRef.addSnapshotListener { value, error ->
                    if (error != null) {
                        trySend(Result.Error(PostError.SERVER_ERROR))
                    } else {
                        trySend(Result.Success(Unit))
                    }
                }

            } catch (e: Exception) {
                e.printStackTrace()
            }
             awaitClose()
        }



    override suspend fun insertTag(tag: Tag) {
        try {
            Result.Loading
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun getAllTags(communityId: String): Flow<List<Tag>> {
        return flow {
            try {
                Result.Loading
            } catch (e: Exception) {
                e.printStackTrace()
                emit(emptyList())
            }
        }
    }

    override suspend fun getUserPosts(userId: String): Result<List<Post>,PostError> {
        return try {
            Result.Loading
        } catch (e: Exception) {
            e.printStackTrace()
            error(PostError.SERVER_ERROR)
        }
    }


    override fun fetchPosts(request: FetchRequest): Flow<Result<List<Post>, PostError>> =
        flow {
            emit(Result.Loading)
            try {
                Result.Loading
            } catch (e: Exception) {
                e.printStackTrace()
                emit(Result.Error(PostError.SERVER_ERROR))

            }
        }

    override fun fetchAllPosts(): Flow<Result<List<Post>, PostError>> = flow {
        emit(Result.Loading)
        try {
            Result.Loading
        } catch (e: Exception) {
            emit(Result.Error(PostError.SERVER_ERROR))
            e.printStackTrace()
        }
    }

    override fun searchByTitle(title: String): Flow<Result<List<Post>, PostError>> = flow {
        emit(Result.Loading)
        try {
            Result.Loading
        } catch (e: Exception) {
            emit(Result.Error(PostError.SERVER_ERROR))
            e.printStackTrace()
        }
    }

    override fun searchByTag(tag: String): Flow<Result<List<Post>, PostError>> = flow {
        emit(Result.Loading)
        try {
            Result.Loading
        } catch (e: Exception) {
            emit(Result.Error(PostError.SERVER_ERROR))
            e.printStackTrace()
        }
    }

    override suspend fun updatePost(request: PostRequest.UpdateRequest): Result<Unit, PostError> =
        try {
            Result.Loading
        } catch (e: Exception) {
            e.printStackTrace()
            Result.Error(PostError.SERVER_ERROR)
        }


    override suspend fun deletePost(request: DeleteRequest): Result<Unit, PostError> =
        try {
            Result.Loading
        } catch (e: Exception) {
            e.printStackTrace()
            Result.Error(PostError.SERVER_ERROR)
        }


    override suspend fun upvotePost(request: UpvoteRequest): Result<Unit, PostError> =
        try {
            Result.Loading
        } catch (e: Exception) {
            e.printStackTrace()
            Result.Error(PostError.SERVER_ERROR)
        }


    override suspend fun downvotePost(request: DownvoteRequest): Result<Unit, PostError> =
        try {
            Result.Loading

        } catch (e: Exception) {
            e.printStackTrace()
            Result.Error(PostError.SERVER_ERROR)
        }


    override suspend fun reportPost(request: PostRequest.ReportRequest): Result<Unit, PostError> =
        try {
            Result.Loading

        } catch (e: Exception) {
            e.printStackTrace()
            Result.Error(PostError.SERVER_ERROR)
        }

}

