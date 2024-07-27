package com.example.gemipost.data.post.source.remote


import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.util.Log
import androidx.compose.ui.platform.LocalContext
import com.example.gemipost.data.post.source.remote.model.Post
import com.example.gemipost.data.post.source.remote.model.PostRequest
import com.example.gemipost.data.post.source.remote.model.PostRequest.DeleteRequest
import com.example.gemipost.data.post.source.remote.model.PostRequest.DownvoteRequest
import com.example.gemipost.data.post.source.remote.model.PostRequest.UpvoteRequest
import com.example.gemipost.data.post.source.remote.model.downvote
import com.example.gemipost.data.post.source.remote.model.upvote
import com.example.gemipost.utils.AppConstants
import com.example.gemipost.utils.PostResults
import com.google.ai.client.generativeai.type.PromptBlockedException
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
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
    override fun createPost(request: PostRequest.CreateRequest): Flow<Result<PostResults, PostResults>> =
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
                        error.printStackTrace()
                        trySend(Result.Error(PostResults.POST_NOT_CREATED))
                    } else {
                        trySend(Result.Success(PostResults.POST_CREATED))
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                Result.Error(PostResults.NETWORK_ERROR)
            }
            awaitClose()
        }

    override fun fetchPosts(): Flow<Result<List<Post>, PostResults>> =
        callbackFlow {
            trySend(Result.Loading)
            try {
                db.collection(AppConstants.DB_Constants.POSTS.name)
                    .addSnapshotListener { value, error ->
                        if (error != null) {
                            trySend(Result.Error(PostResults.POST_FETCHED_FAILED))
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
                trySend(Result.Error(PostResults.NETWORK_ERROR))
            }
            awaitClose()
        }

    override fun fetchPostById(id: String): Flow<Result<Post, PostResults>> = callbackFlow {
        try {
            val docRef = colRef.document(id)
            docRef.addSnapshotListener { value, error ->
                if (error != null) {
                    trySend(Result.Error(PostResults.POST_NOT_FOUND))
                } else {
                    println("Value: ${value!!.toObject(Post::class.java)}")
                    value.toObject(Post::class.java)?.let{
                        trySend(Result.Success(it))
                    }
                }
            }
        } catch (e: Exception) {
            Result.Error(PostResults.NETWORK_ERROR)
            e.printStackTrace()
        }
        awaitClose()
    }





    override suspend fun updatePost(request: PostRequest.UpdateRequest): Result<PostResults, PostResults> =
        try {
            println(request.post)
            Result.Loading
            if (request.post.id.isBlank()) {
                Result.Error(PostResults.POST_NOT_UPDATED)
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
            Result.Success(PostResults.POST_UPDATED)
        } catch (e: Exception) {
            e.printStackTrace()
            Result.Error(PostResults.NETWORK_ERROR)
        }


    override suspend fun deletePost(request: DeleteRequest): Result<PostResults, PostResults> =
        try {
            val docRef = colRef.document(request.postId)
            docRef.delete().await()
            Result.Success(PostResults.POST_DELETED)
        } catch (e: Exception) {
            e.printStackTrace()
            Result.Error(PostResults.NETWORK_ERROR)
        }


    override suspend fun upvotePost(request: UpvoteRequest): Result<Unit, PostResults> =
        try {
            val docRef = colRef.document(request.postId)
            val result= docRef.get().await().toObject(Post::class.java)
            if(result==null)  Result.Error(PostResults.POST_NOT_FOUND)
            else  {
                docRef.set(result.upvote(request.userId)).await()
                Result.Success(Unit)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Result.Error(PostResults.NETWORK_ERROR)
        }


    override suspend fun downvotePost(request: DownvoteRequest): Result<Unit, PostResults> =
        try {
            val docRef = colRef.document(request.postId)
            val result= docRef.get().await().toObject(Post::class.java)
            if(result==null)  Result.Error(PostResults.POST_NOT_FOUND)
            else  {
                docRef.set(result.downvote(request.userId)).await()
                Result.Success(Unit)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Result.Error(PostResults.NETWORK_ERROR)
        }


    override suspend fun reportPost(postId: String, title: String, body: String, images: List<Bitmap>): Result<PostResults, PostResults> {
        return try {
            val shouldBeRemoved = if (images.isEmpty()) {
                moderationSource.validateText(title, body)
            } else {
                moderationSource.validateTextWithImages(title, body, images = images)
            }
            if (shouldBeRemoved) {
                removePost(postId)
            }
            Result.Success(PostResults.POST_REPORTED)
        } catch (e: Exception) {
            e.printStackTrace()
            if(e is PromptBlockedException){
                removePost(postId)
                Result.Success(PostResults.POST_REPORTED)
            } else {
                Log.d("seerde","exception message: ${e.message}")
                Result.Error(PostResults.NETWORK_ERROR)
            }
        }
    }

    private suspend fun removePost(postId: String) {
        colRef.document(postId).delete().await()
    }

}
