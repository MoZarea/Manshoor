package com.example.gemipost.data.post.source.remote

import com.example.gemipost.data.post.source.remote.model.Reply
import com.example.gemipost.data.post.source.remote.model.downvote
import com.example.gemipost.data.post.source.remote.model.upvote
import com.example.gemipost.utils.AppConstants
import com.google.firebase.firestore.FirebaseFirestore
import com.gp.socialapp.util.ReplyError
import com.gp.socialapp.util.Result
import com.gp.socialapp.util.Result.Companion.failure
import com.gp.socialapp.util.Result.Companion.success
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class ReplyRemoteDataSourceImpl(
    private val db: FirebaseFirestore
) : ReplyRemoteDataSource {
    private val colRef = db.collection(AppConstants.DB_Constants.REPLIES.name)
    override suspend fun createReply(reply: Reply): Result<Unit, ReplyError> =
        try {
            val docRef = colRef.document()
            val reply = reply.copy(id = docRef.id)
            docRef.set(reply)
            success(Unit)
        } catch (e: Exception) {
            e.printStackTrace()
            failure(ReplyError.SERVER_ERROR)
        }


    override fun fetchReplies(postId: String): Flow<Result<List<Reply>, ReplyError>> =
        callbackFlow {
            trySend(Result.Loading)
            try {
                val listener = colRef.whereEqualTo("postId", postId)
                    .addSnapshotListener { value, error ->
                        if (error != null) {
                            trySend(Result.Error(ReplyError.SERVER_ERROR))
                        } else {
                            val replies = value?.toObjects(Reply::class.java) ?: emptyList()
                            trySend(Result.Success(replies))
                        }
                    }

            } catch (e: Exception) {
                e.printStackTrace()
                trySend(Result.Error(ReplyError.SERVER_ERROR))
            }
            awaitClose()
        }

    override suspend fun updateReply(
        replyId: String,
        replyContent: String
    ): Result<Unit, ReplyError> =
        try {
            val docRef = colRef.document(replyId)
            val reply = docRef.get().await().toObject(Reply::class.java)
            docRef.set(reply!!.copy(content = replyContent))
            success(Unit)
        } catch (e: Exception) {
            e.printStackTrace()
            Result.Error(ReplyError.SERVER_ERROR)
        }


    override suspend fun deleteReply(replyId: String): Result<Unit, ReplyError> =
        try {
            val docRef = colRef.document(replyId)
            docRef.delete().await()
            success(Unit)
        } catch (e: Exception) {
            e.printStackTrace()
            Result.Error(ReplyError.SERVER_ERROR)
        }


    override suspend fun upvoteReply(replyId: String, userId: String): Result<Unit, ReplyError> =
        try {
            val docRef = colRef.document(replyId)
            docRef.get().await().toObject(Reply::class.java)?.let {
                docRef.set(
                    it.upvote(userId)
                )
            }
            success(Unit)
        } catch (e: Exception) {
            e.printStackTrace()
            Result.Error(ReplyError.SERVER_ERROR)
        }


    override suspend fun downvoteReply(replyId: String, userId: String): Result<Unit, ReplyError> =
        try {
            val docRef = colRef.document(replyId)
            docRef.get().await().toObject(Reply::class.java)?.let {
                docRef.set(
                    it.downvote(userId)
                )
            }
            success(Unit)
        } catch (e: Exception) {
            e.printStackTrace()
            Result.Error(ReplyError.SERVER_ERROR)
        }

    override suspend fun reportReply(
        replyId: String,
        replyContent: String,
        reporterId: String
    ): Result<Unit, ReplyError> {
        TODO("Not yet implemented")
    }

}