package com.example.gemipost.data.post.repository

import com.example.gemipost.data.post.source.remote.model.Reply
import com.example.gemipost.utils.PostResults
import com.example.gemipost.utils.ReplyResults
import com.gp.socialapp.util.Result
import kotlinx.coroutines.flow.Flow

interface ReplyRepository {
    fun getReplies(postId: String): Flow<Result<List<Reply>, ReplyResults>>
    suspend fun updateReply(replyId: String, replyContent: String): Result<ReplyResults, ReplyResults>
    suspend fun deleteReply(replyId: String): Result<ReplyResults, ReplyResults>
    suspend fun upvoteReply(
        replyId: String, currentUserId: String
    ): Result<Unit, ReplyResults>

    suspend fun downvoteReply(
        replyId: String, currentUserId: String
    ): Result<Unit, ReplyResults>

    suspend fun createReply(reply: Reply): Result<ReplyResults, ReplyResults>
    suspend fun reportReply(
        replyId: String, replyContent: String
    ): Result<ReplyResults, ReplyResults>
}