package com.example.gemipost.data.post.source.remote

import android.net.Uri
import com.example.gemipost.data.post.source.remote.model.Post
import com.example.gemipost.data.post.source.remote.model.PostRequest
import com.example.gemipost.data.post.source.remote.model.PostRequest.DeleteRequest
import com.example.gemipost.data.post.source.remote.model.PostRequest.DownvoteRequest
import com.example.gemipost.data.post.source.remote.model.PostRequest.UpvoteRequest
import com.example.gemipost.utils.AppConstants
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.gp.socialapp.util.PostError
import com.gp.socialapp.util.Result
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

class PostRemoteDataSourceImpl(
    private val db: FirebaseFirestore,
    private val storage: FirebaseStorage
) : PostRemoteDataSource {
    private val colRef = db.collection(AppConstants.DB_Constants.POSTS.name)
    override fun createPost(request: PostRequest.CreateRequest): Flow<Result<Unit, PostError>> =
        callbackFlow {
            trySend(Result.Loading)
            try {
                val docRef = colRef.document()
                val post = request.post.copy(
                    attachments = request.post.attachments.map { uriString ->
                        var downloadUrl: String? = null
                        val fileUri = Uri.parse(uriString)
                        val uploadTask =
                            storage.reference.child("POST_IMAGES/${docRef.id}/${fileUri.lastPathSegment}")
                                .putFile(fileUri)
                        val taskSnapshot =
                            uploadTask.await() // Await for the upload to complete
                        downloadUrl = taskSnapshot.metadata?.reference?.downloadUrl?.await()
                            .toString() // Get the download URL
                        downloadUrl
                            ?: uriString // Use the download URL or fallback to the original URI string if null
                    }
                )
                docRef.set(post.copy(id = docRef.id))
                val listener = docRef.addSnapshotListener { value, error ->
                    if (error != null) {
                        trySend(Result.Error(PostError.SERVER_ERROR))
                    } else {
                        trySend(Result.Success(Unit))
                    }
                }
                awaitClose {
                    listener.remove()
                }

            } catch (e: Exception) {
                e.printStackTrace()
            }
            awaitClose()
        }

    override fun fetchPosts(): Flow<Result<List<Post>, PostError>> =
        callbackFlow {
            trySend(Result.Loading)
            try {
                Result.Loading
                val listener = db.collection(AppConstants.DB_Constants.POSTS.name)
                    .addSnapshotListener { value, error ->
                        if (error != null) {
                            trySend(Result.Error(PostError.SERVER_ERROR))
                        } else {
                            val posts = mutableListOf<Post>()
                            for (doc in value!!) {
                                posts.add(doc.toObject(Post::class.java))
                            }
                            trySend(Result.Success(posts))

                        }
                    }
                awaitClose {
                    listener.remove()
                }
            } catch (e: Exception) {
                e.printStackTrace()
                trySend(Result.Error(PostError.SERVER_ERROR))

            }
        }

    override fun fetchPostById(id: String): Flow<Result<Post, PostError>> = callbackFlow {
        try {

            val docRef = colRef.document(id)
            val listener = docRef.addSnapshotListener { value, error ->
                if (error != null) {
                    trySend(Result.Error(PostError.SERVER_ERROR))
                } else {
                    println("Value: ${value!!.toObject(Post::class.java)}")
                    trySend(Result.Success(value!!.toObject(Post::class.java)!!))
                }
            }
            awaitClose {
                listener.remove()
            }
        } catch (e: Exception) {
            Result.Error(PostError.SERVER_ERROR)
            e.printStackTrace()
        }
    }


    override fun searchByTitle(title: String): Flow<Result<List<Post>, PostError>> = callbackFlow {
        trySend(Result.Loading)
        try {
            Result.Loading
            val query = colRef.whereEqualTo("title", title)
            val listener = query.addSnapshotListener { value, error ->
                if (error != null) {
                    trySend(Result.Error(PostError.SERVER_ERROR))
                } else {
                    val posts = mutableListOf<Post>()
                    for (doc in value!!) {
                        posts.add(doc.toObject(Post::class.java))
                    }
                    trySend(Result.Success(posts))
                }
            }
            awaitClose {
                listener.remove()
            }
        } catch (e: Exception) {
            trySend(Result.Error(PostError.SERVER_ERROR))
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
            println(request.post)
            Result.Loading
            if(request.post.id.isBlank()){
                 Result.Error(PostError.SERVER_ERROR)
            }
            val post = request.post
            val docRef = colRef.document(post.id)
            val updatedPost = post.copy(
                attachments = post.attachments.filter{it.startsWith("content://")}.map { uriString ->
                    var downloadUrl: String? = null
                    val fileUri = Uri.parse(uriString)
                    val uploadTask =
                        storage.reference.child("POST_IMAGES/${post.id}/${fileUri.lastPathSegment}")
                            .putFile(fileUri)
                    val taskSnapshot =
                        uploadTask.await() // Await for the upload to complete
                    downloadUrl = taskSnapshot.metadata?.reference?.downloadUrl?.await()
                        .toString() // Get the download URL
                    downloadUrl
                        ?: uriString // Use the download URL or fallback to the original URI string if null
                }
            )
            docRef.set(updatedPost)
            Result.Success(Unit)
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

