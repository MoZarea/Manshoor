package com.example.gemipost.data.post.source.remote


import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import androidx.compose.ui.platform.LocalContext
import com.example.gemipost.data.post.source.remote.model.Post
import com.example.gemipost.data.post.source.remote.model.PostRequest
import com.example.gemipost.data.post.source.remote.model.PostRequest.DeleteRequest
import com.example.gemipost.data.post.source.remote.model.PostRequest.DownvoteRequest
import com.example.gemipost.data.post.source.remote.model.PostRequest.UpvoteRequest
import com.example.gemipost.data.post.source.remote.model.downvote
import com.example.gemipost.data.post.source.remote.model.upvote
import com.example.gemipost.utils.AppConstants
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.gp.socialapp.util.PostError
import com.gp.socialapp.util.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext


class PostRemoteDataSourceImpl(
    private val db: FirebaseFirestore,
    private val storage: FirebaseStorage,
    private val moderationSource: ModerationRemoteDataSource
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
                docRef.addSnapshotListener { value, error ->
                    if (error != null) {
                        trySend(Result.Error(PostError.SERVER_ERROR))
                    } else {
                        trySend(Result.Success(Unit))
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                Result.Error(PostError.SERVER_ERROR)
            }
            awaitClose()
        }

    override fun fetchPosts(): Flow<Result<List<Post>, PostError>> =
        callbackFlow {
            trySend(Result.Loading)
            try {
                Result.Loading
                db.collection(AppConstants.DB_Constants.POSTS.name)
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

            } catch (e: Exception) {
                e.printStackTrace()
                trySend(Result.Error(PostError.SERVER_ERROR))
            }
            awaitClose()
        }

    override fun fetchPostById(id: String): Flow<Result<Post, PostError>> = callbackFlow {
        try {
            val docRef = colRef.document(id)
            val listener = docRef.addSnapshotListener { value, error ->
                if (error != null) {
                    trySend(Result.Error(PostError.SERVER_ERROR))
                } else {
                    println("Value: ${value!!.toObject(Post::class.java)}")
                    trySend(Result.Success(value.toObject(Post::class.java)!!))
                }
            }
        } catch (e: Exception) {
            Result.Error(PostError.SERVER_ERROR)
            e.printStackTrace()
        }
        awaitClose()
    }


    override fun searchByTitle(title: String): Flow<Result<List<Post>, PostError>> = callbackFlow {
        trySend(Result.Loading)
        try {
            Result.Loading
            //TODO: Implement search by title :Next Feature
        } catch (e: Exception) {
            trySend(Result.Error(PostError.SERVER_ERROR))
            e.printStackTrace()
        }
        awaitClose()


    }

    override fun searchByTag(tag: String): Flow<Result<List<Post>, PostError>> = callbackFlow {
        trySend(Result.Loading)
        try {
            Result.Loading
            //TODO: Implement search by tag :Next Feature
        } catch (e: Exception) {
            trySend(Result.Error(PostError.SERVER_ERROR))
            e.printStackTrace()
        }
        awaitClose()
    }

    override suspend fun updatePost(request: PostRequest.UpdateRequest): Result<Unit, PostError> =
        try {
            println(request.post)
            Result.Loading
            if (request.post.id.isBlank()) {
                Result.Error(PostError.SERVER_ERROR)
            }
            val post = request.post
            val docRef = colRef.document(post.id)
            val updatedPost = post.copy(
                attachments = post.attachments.filter { it.startsWith("content://") }
                    .map { uriString ->
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
            val docRef = colRef.document(request.postId)
            docRef.delete().await()
            Result.Success(Unit)
        } catch (e: Exception) {
            e.printStackTrace()
            Result.Error(PostError.SERVER_ERROR)
        }


    override suspend fun upvotePost(request: UpvoteRequest): Result<Unit, PostError> =
        try {
            val docRef = colRef.document(request.postId)
            docRef.get().await().toObject(Post::class.java)?.let {
                docRef.set(it.upvote(request.userId))
            }
            Result.Success(Unit)
        } catch (e: Exception) {
            e.printStackTrace()
            Result.Error(PostError.SERVER_ERROR)
        }


    override suspend fun downvotePost(request: DownvoteRequest): Result<Unit, PostError> =
        try {
            val docRef = colRef.document(request.postId)
            docRef.get().await().toObject(Post::class.java)?.let {
                docRef.set(it.downvote(request.userId))
            }
            Result.Success(Unit)
        } catch (e: Exception) {
            e.printStackTrace()
            Result.Error(PostError.SERVER_ERROR)
        }


    override suspend fun reportPost(postId: String, title: String, body: String, images: List<Bitmap>): Result<Unit, PostError> {
        return try {
            val shouldBeRemoved = if (images.isNotEmpty()) {
                moderationSource.validateText(title, body)
            } else {
                moderationSource.validateTextWithImages(title, body, images = images)
            }
            if (shouldBeRemoved) {
                removePost(postId)
            }
            Result.Success(Unit)
        } catch (e: Exception) {
            e.printStackTrace()
            Result.Error(PostError.SERVER_ERROR)
        }
    }

    private suspend fun removePost(postId: String) {
        colRef.document(postId).delete().await()
    }

}
