package com.example.gemipost.data.post.source.remote

import com.example.gemipost.data.post.source.remote.model.Reply
import com.example.gemipost.data.post.source.remote.model.downvote
import com.example.gemipost.data.post.source.remote.model.upvote
import com.example.gemipost.utils.AppConstants
import com.example.gemipost.utils.ReplyResults
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.gp.socialapp.util.Result
import com.gp.socialapp.util.Result.Companion.failure
import com.gp.socialapp.util.Result.Companion.success
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class ReplyRemoteDataSourceImpl(
    private val db: FirebaseFirestore,
    private val moderationSource: ModerationRemoteDataSource
) : ReplyRemoteDataSource {
    private val repColRef = db.collection(AppConstants.DB_Constants.REPLIES.name)
    private val postColRef = db.collection(AppConstants.DB_Constants.POSTS.name)

    override suspend fun createReply(reply: Reply): Result<ReplyResults, ReplyResults> =
        try {
            val docRef = repColRef.document()
            val reply = reply.copy(id = docRef.id)
            docRef.set(reply)
            postColRef.document(reply.postId).update("replyCount", FieldValue.increment(1))
            success(ReplyResults.REPLY_CREATED)
        } catch (e: Exception) {
            e.printStackTrace()
            failure(ReplyResults.NETWORK_ERROR)
        }


    override fun fetchReplies(postId: String): Flow<Result<List<Reply>, ReplyResults>> =
        callbackFlow {
            trySend(Result.Loading)
            try {
                repColRef.whereEqualTo("postId", postId)
                    .addSnapshotListener { value, error ->
                        if (error != null) {
                            trySend(Result.Error(ReplyResults.REPLY_FETCHED_FAILED))
                        } else {
                            val replies = value?.toObjects(Reply::class.java) ?: emptyList()
                            trySend(Result.Success(replies))
                        }
                    }

            } catch (e: Exception) {
                e.printStackTrace()
                trySend(Result.Error(ReplyResults.NETWORK_ERROR))
            }
            awaitClose()
        }

    override suspend fun updateReply(
        replyId: String,
        replyContent: String
    ): Result<ReplyResults, ReplyResults> =
        try {
            val docRef = repColRef.document(replyId)
            val reply = docRef.get().await().toObject(Reply::class.java)
            docRef.set(reply!!.copy(content = replyContent))
            success(ReplyResults.REPLY_UPDATED)
        } catch (e: Exception) {
            e.printStackTrace()
            Result.Error(ReplyResults.NETWORK_ERROR)
        }


    override suspend fun deleteReply(
        replyId: String,
        postId: String,
    ): Result<ReplyResults, ReplyResults> =
        try {
            val docRef = repColRef.document(replyId)
            docRef.delete().await()
            postColRef.document(postId).update("replyCount", FieldValue.increment(-1))
            success(ReplyResults.REPLY_DELETED)
        } catch (e: Exception) {
            e.printStackTrace()
            Result.Error(ReplyResults.NETWORK_ERROR)
        }


    override suspend fun upvoteReply(replyId: String, userId: String): Result<Unit, ReplyResults> =
        try {
            val docRef = repColRef.document(replyId)
            docRef.get().await().toObject(Reply::class.java)?.let {
                docRef.set(
                    it.upvote(userId)
                )
            }
            success(Unit)
        } catch (e: Exception) {
            e.printStackTrace()
            Result.Error(ReplyResults.NETWORK_ERROR)
        }


    override suspend fun downvoteReply(
        replyId: String,
        userId: String
    ): Result<Unit, ReplyResults> =
        try {
            val docRef = repColRef.document(replyId)
            docRef.get().await().toObject(Reply::class.java)?.let {
                docRef.set(
                    it.downvote(userId)
                )
            }
            success(Unit)
        } catch (e: Exception) {
            e.printStackTrace()
            Result.Error(ReplyResults.NETWORK_ERROR)
        }

    override suspend fun reportReply(
        replyId: String,
        replyContent: String
    ): Result<ReplyResults, ReplyResults> {
        return try {
            val shouldBeRemoved = moderationSource.validateText(replyContent)
            if (shouldBeRemoved) {
                removeReply(replyId)
            }
            Result.Success(ReplyResults.REPLY_REPORTED)
        } catch (e: Exception) {
            e.printStackTrace()
            Result.Error(ReplyResults.NETWORK_ERROR)
        }
    }

    private suspend fun removeReply(replyId: String) {
        repColRef.document(replyId).delete().await()
    }
}