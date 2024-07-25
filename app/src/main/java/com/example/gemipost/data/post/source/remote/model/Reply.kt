package com.example.gemipost.data.post.source.remote.model

import com.example.gemipost.utils.LocalDateTimeUtil.now
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.serialization.Serializable

@Serializable
data class Reply(
    val id: String = "",
    val authorID: String = "",
    val postId: String = "",
    val parentReplyId: String = "-1",
    val content: String = "",
    val votes: Int = 0,
    val depth: Int = -1,
    val createdAt: Long = LocalDateTime.now().toInstant(TimeZone.UTC).toEpochMilliseconds(),
    val deleted: Boolean = false,
    val upvoted: List<String> = emptyList(),
    val downvoted: List<String> = emptyList(),
    val authorName: String = "",
    val authorImageLink: String = "",
    val collapsed: Boolean = false,
    val editStatus: Boolean = false,
    val moderationStatus: String = "SAFE"
)
fun Reply.isUpvoted(userId: String): Boolean = userId in upvoted
fun Reply.isDownvoted(userId: String): Boolean = userId in downvoted
fun Reply.upvote(userId: String): Reply {
    if(isUpvoted(userId)) return copy(votes = votes - 1, upvoted = upvoted - userId)
    else if (isDownvoted(userId)) return copy(votes = votes + 2, upvoted = upvoted + userId, downvoted = downvoted - userId )
    return copy(votes = votes + 1, upvoted = upvoted + userId)
}
fun Reply.downvote(userId: String): Reply {
    if(isDownvoted(userId)) return copy(votes = votes + 1, downvoted = downvoted - userId)
    else if (isUpvoted(userId)) return copy(votes = votes - 2, downvoted = downvoted + userId, upvoted = upvoted - userId )
    return copy(votes = votes - 1, downvoted = downvoted + userId)
}
