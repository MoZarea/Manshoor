package com.example.gemipost.data.post.repository

import com.example.gemipost.data.post.source.remote.ReplyRemoteDataSource
import com.example.gemipost.data.post.source.remote.model.Reply
import com.example.gemipost.utils.ReplyResults
import com.gp.socialapp.util.Result
import kotlinx.coroutines.flow.Flow

class ReplyRepositoryImpl(
    private val remoteSource: ReplyRemoteDataSource,
) : ReplyRepository {
    override fun getReplies(postId: String): Flow<Result<List<Reply>, ReplyResults>> {
        return remoteSource.fetchReplies(postId)
    }

    override suspend fun updateReply(replyId: String, replyContent: String): Result<ReplyResults, ReplyResults> {
        return remoteSource.updateReply(replyId, replyContent)
    }

    override suspend fun deleteReply(replyId: String): Result<ReplyResults, ReplyResults> {
        return remoteSource.deleteReply(replyId)
    }

    override suspend fun upvoteReply(
        replyId: String,
        currentUserId: String
    ): Result<Unit, ReplyResults> {
        return remoteSource.upvoteReply(replyId, currentUserId)
    }

    override suspend fun downvoteReply(
        replyId: String,
        currentUserId: String
    ): Result<Unit, ReplyResults> {
        return remoteSource.downvoteReply(replyId, currentUserId)
    }

    override suspend fun createReply(reply: Reply): Result<ReplyResults, ReplyResults> {
        return remoteSource.createReply(reply)
    }

    override suspend fun reportReply(
        replyId: String,
        replyContent: String,
        reporterId: String
    ): Result<ReplyResults, ReplyResults> {
        return remoteSource.reportReply(replyId, replyContent, reporterId)
    }
}