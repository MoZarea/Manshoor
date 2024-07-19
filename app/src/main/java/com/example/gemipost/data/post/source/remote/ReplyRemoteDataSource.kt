package com.example.gemipost.data.post.source.remote

import com.example.gemipost.data.post.source.remote.model.Reply
import com.example.gemipost.data.post.source.remote.model.ReplyRequest
import com.gp.socialapp.util.ReplyError
import com.gp.socialapp.util.Result
import kotlinx.coroutines.flow.Flow

interface ReplyRemoteDataSource {
    suspend fun createReply(request: ReplyRequest.CreateRequest): Result<Unit, ReplyError>
    fun fetchReplies(request: ReplyRequest.FetchRequest): Flow<Result<List<Reply>, ReplyError>>
    suspend fun updateReply(request: ReplyRequest.UpdateRequest): Result<Unit, ReplyError>
    suspend fun deleteReply(request: ReplyRequest.DeleteRequest): Result<Unit, ReplyError>
    suspend fun upvoteReply(request: ReplyRequest.UpvoteRequest): Result<Unit, ReplyError>
    suspend fun downvoteReply(request: ReplyRequest.DownvoteRequest): Result<Unit, ReplyError>
    suspend fun reportReply(request: ReplyRequest.ReportRequest): Result<Unit, ReplyError>
}