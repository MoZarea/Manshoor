package com.example.gemipost.data.post.repository

import android.graphics.BitmapFactory
import com.example.gemipost.data.post.source.remote.ReplyRemoteDataSource
import com.example.gemipost.data.post.source.remote.model.Reply
import com.example.gemipost.utils.ReplyResults
import com.gp.socialapp.util.Result
import kotlinx.coroutines.flow.Flow
import java.io.File

class ReplyRepositoryImpl(
    private val remoteSource: ReplyRemoteDataSource,
) : ReplyRepository {
    override fun getReplies(postId: String): Flow<Result<List<Reply>, ReplyResults>> {
        return remoteSource.fetchReplies(postId)
    }

    override suspend fun updateReply(replyId: String, replyContent: String): Result<ReplyResults, ReplyResults> {
        return remoteSource.updateReply(replyId, replyContent)
    }

    override suspend fun deleteReply(replyId: String,postId: String): Result<ReplyResults, ReplyResults> {
        return remoteSource.deleteReply(replyId, postId)
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

    override suspend fun createReply(reply: Reply, currentUserId: String): Result<ReplyResults, ReplyResults> {
        return remoteSource.createReply(reply, currentUserId)
    }

    override suspend fun reportReply(
        replyId: String,
        replyContent: String
    ): Result<ReplyResults, ReplyResults> {
        return remoteSource.reportReply(replyId, replyContent)
    }
}