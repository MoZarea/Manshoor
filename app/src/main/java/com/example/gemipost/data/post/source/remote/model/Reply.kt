package com.example.gemipost.data.post.source.remote.model

import com.gp.socialapp.util.LocalDateTimeUtil.now
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
