package com.example.gemipost.data.post.source.local.model

import com.example.gemipost.data.post.source.remote.model.Post
import com.example.gemipost.data.post.source.remote.model.PostAttachment.Companion.toPostFile
import com.example.gemipost.data.post.source.remote.model.Tag.Companion.toTag
import com.gp.socialapp.util.LocalDateTimeUtil.now
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant

data class PostEntity(
    val communityID: String = "",
    val replyCount: Int = 0,
    val authorName: String = "",
    val authorPfp: String = "",
    val id: String = "",
    val authorID: String = "",
    val createdAt: Long = LocalDateTime.now().toInstant(TimeZone.UTC).epochSeconds,
    val title: String = "",
    val body: String = "",
    val votes: Int = 0,
    val downvoted: String = "",
    val upvoted: String = "",
    val moderationStatus: String = "submitted",
    val editedStatus: Int = 0,
    val tags: String = "",
    val type: String = "general",
    val attachments: String = "",
    val lastModified: Long = LocalDateTime.now().toInstant(TimeZone.UTC).epochSeconds
) {
    companion object {
        fun PostEntity.toPost(): Post {
            return with(this) {
                Post(
                    replyCount = replyCount,
                    authorName = authorName,
                    authorPfp = authorPfp,
                    id = id,
                    authorID = authorID,
                    createdAt = createdAt,
                    title = title,
                    body = body,
                    votes = votes,
                    downvoted = downvoted.split(",").filter { it.isNotBlank() },
                    upvoted = upvoted.split(",").filter { it.isNotBlank() },
                    moderationStatus = moderationStatus,
                    editedStatus = editedStatus == 1,
                    tags = tags.split(",").filter { it.isNotBlank() }.map { it.toTag() },
                    type = type,
                    attachments = attachments.split(",").filter { it.isNotBlank() }
                        .map { it.toPostFile() },
                    lastModified = lastModified,
                    communityID = communityID
                )
            }
        }
    }
}
