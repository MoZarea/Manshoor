package com.example.gemipost.data.post.source.remote

import com.example.gemipost.data.post.source.remote.model.Reply
import com.example.gemipost.data.post.source.remote.model.ReplyRequest
import com.example.gemipost.data.post.util.endPoint
import com.gp.socialapp.util.ReplyError
import com.gp.socialapp.util.Result
import com.gp.socialapp.util.Result.Companion.success
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.flow.Flow

class ReplyRemoteDataSourceImpl(
    private val client: HttpClient
) : ReplyRemoteDataSource {

    override suspend fun createReply(reply: Reply): Result<Unit, ReplyError> {
        TODO("Not yet implemented")
    }


    override fun fetchReplies(postId: String): Flow<Result<List<Reply>, ReplyError>> {
        TODO("Not yet implemented")
    }

    override suspend fun updateReply(
        replyId: String,
        replyContent: String
    ): Result<Unit, ReplyError> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteReply(replyId: String): Result<Unit, ReplyError> {
        TODO("Not yet implemented")
    }

    override suspend fun upvoteReply(
        replyId: String,
        currentUserId: String
    ): Result<Unit, ReplyError> {
        TODO("Not yet implemented")
    }

    override suspend fun downvoteReply(
        replyId: String,
        currentUserId: String
    ): Result<Unit, ReplyError> {
        TODO("Not yet implemented")
    }


    override suspend fun reportReply(
        replyId: String,
        replyContent: String,
        reporterId: String
    ): Result<Unit, ReplyError> {
        TODO("Not yet implemented")
    }

}