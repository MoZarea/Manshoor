package com.example.gemipost.data.post.source.remote

import com.example.gemipost.data.post.source.remote.model.Reply
import com.example.gemipost.data.post.source.remote.model.ReplyRequest
import com.gp.socialapp.util.ReplyError
import com.gp.socialapp.util.Result
import kotlinx.coroutines.flow.Flow

interface ReplyRemoteDataSource {
    suspend fun createReply(reply: Reply): Result<Unit, ReplyError>
    fun fetchReplies(postId: String): Flow<Result<List<Reply>, ReplyError>>
    suspend fun updateReply(replyId: String, replyContent: String): Result<Unit, ReplyError>
    suspend fun deleteReply(replyId: String): Result<Unit, ReplyError>
    suspend fun upvoteReply(
        replyId: String,
        currentUserId: String): Result<Unit, ReplyError>
    suspend fun downvoteReply(replyId: String, currentUserId: String): Result<Unit, ReplyError>
    suspend fun reportReply(
        replyId: String, replyContent: String, reporterId: String
    ): Result<Unit, ReplyError>
}