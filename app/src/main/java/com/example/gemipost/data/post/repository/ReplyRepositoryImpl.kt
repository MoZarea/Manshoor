package com.example.gemipost.data.post.repository

import com.example.gemipost.data.post.source.remote.ReplyRemoteDataSource
import com.example.gemipost.data.post.source.remote.model.Reply
import com.example.gemipost.data.post.source.remote.model.ReplyRequest
import com.gp.socialapp.util.ReplyError
import com.gp.socialapp.util.Result
import kotlinx.coroutines.flow.Flow

class ReplyRepositoryImpl(
    private val remoteSource: ReplyRemoteDataSource,
) : ReplyRepository {
    override fun getReplies(postId: String): Flow<Result<List<Reply>, ReplyError>> {
        return remoteSource.fetchReplies(postId)
    }

    override suspend fun updateReply(replyId: String, replyContent: String): Result<Unit, ReplyError> {
        val request = ReplyRequest.UpdateRequest(replyId, replyContent)
        return remoteSource.updateReply(replyId, replyContent)
    }

    override suspend fun deleteReply(replyId: String): Result<Unit, ReplyError> {
        val request = ReplyRequest.DeleteRequest(replyId)
        return remoteSource.deleteReply(replyId)
    }

    override suspend fun upvoteReply(
        replyId: String,
        currentUserId: String
    ): Result<Unit, ReplyError> {
        return remoteSource.upvoteReply(replyId, currentUserId)
    }

    override suspend fun downvoteReply(
        replyId: String,
        currentUserId: String
    ): Result<Unit, ReplyError> {
        val request = ReplyRequest.DownvoteRequest(replyId, currentUserId)
        return remoteSource.downvoteReply(replyId, currentUserId)
    }

    override suspend fun createReply(reply: Reply): Result<Unit, ReplyError> {
        return remoteSource.createReply(reply)
    }

    override suspend fun reportReply(
        replyId: String,
        replyContent: String,
        reporterId: String
    ): Result<Unit, ReplyError> {
        return remoteSource.reportReply(replyId, replyContent, reporterId)
    }
}