package com.example.gemipost.data.post.source.remote.model

import android.net.Uri
import android.os.Parcelable
import com.example.gemipost.data.post.source.remote.model.PostAttachment.Companion
import com.example.gemipost.data.post.util.PostPopularityUtils
import com.example.gemipost.utils.LocalDateTimeUtil.now
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable
import org.kodein.di.bindings.ErasedContext.type

@Parcelize
@Serializable
data class Post(
    val replyCount: Int = 0,
    val authorName: String = "",
    val authorPfp: String = "",
    val id: String = "",
    val authorID: String = "",
    val createdAt: Long = LocalDateTime.now().toInstant(TimeZone.UTC).toEpochMilliseconds(),
    val title: String = "",
    val body: String = "",
    val votes: Int = 0,
    val downvoted: List<String> = emptyList(),
    val upvoted: List<String> = emptyList(),
    val tags: List<Tag> = emptyList(),
    val attachments: List<String> = emptyList(),
    val moderationStatus: String = "",
) : Parcelable {}

val sortByVotes = compareByDescending<Post> {
    PostPopularityUtils.calculateInteractionValue(
        it.votes,
        it.replyCount
    )
}
fun Post.isUpvoted(userId: String): Boolean = userId in upvoted
fun Post.isDownvoted(userId: String): Boolean = userId in downvoted
fun Post.upvote(userId: String): Post {
    if(isUpvoted(userId)) return copy(votes = votes - 1, upvoted = upvoted - userId)
    else if (isDownvoted(userId)) return copy(votes = votes + 2, upvoted = upvoted + userId, downvoted = downvoted - userId )
    return copy(votes = votes + 1, upvoted = upvoted + userId)
}
fun Post.downvote(userId: String): Post {
    if(isDownvoted(userId)) return copy(votes = votes + 1, downvoted = downvoted - userId)
    else if (isUpvoted(userId)) return copy(votes = votes - 2, downvoted = downvoted + userId, upvoted = upvoted - userId )
    return copy(votes = votes - 1, downvoted = downvoted + userId)
}


