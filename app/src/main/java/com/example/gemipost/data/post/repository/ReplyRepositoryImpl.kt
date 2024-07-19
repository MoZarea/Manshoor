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
        val request = ReplyRequest.FetchRequest(postId)
        return remoteSource.fetchReplies(request)
    }

    override suspend fun updateReply(replyId: String, replyContent: String): Result<Unit, ReplyError> {
        val request = ReplyRequest.UpdateRequest(replyId, replyContent)
        return remoteSource.updateReply(request)
    }

    override suspend fun deleteReply(replyId: String): Result<Unit, ReplyError> {
        val request = ReplyRequest.DeleteRequest(replyId)
        return remoteSource.deleteReply(request)
    }

    override suspend fun upvoteReply(
        replyId: String,
        currentUserId: String
    ): Result<Unit, ReplyError> {
        val request = ReplyRequest.UpvoteRequest(replyId, currentUserId)
        return remoteSource.upvoteReply(request)
    }

    override suspend fun downvoteReply(
        replyId: String,
        currentUserId: String
    ): Result<Unit, ReplyError> {
        val request = ReplyRequest.DownvoteRequest(replyId, currentUserId)
        return remoteSource.downvoteReply(request)
    }

    override suspend fun insertReply(reply: Reply): Result<Unit, ReplyError> {
        val request = ReplyRequest.CreateRequest(reply)
        println("request: $request")
        return remoteSource.createReply(request)
    }

    override suspend fun reportReply(
        replyId: String,
        reporterId: String
    ): Result<Unit, ReplyError> {
        val request = ReplyRequest.ReportRequest(replyId, reporterId)
        return remoteSource.reportReply(request)
    }

//    override suspend fun getReplyCountByPostId(postId: String): Result<Int> {
//        return remoteSource.getReplyCountByPostId(postId)
//    }
}