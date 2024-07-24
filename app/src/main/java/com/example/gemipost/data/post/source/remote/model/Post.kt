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
//        val sortByDate = compareByDescending<Post>{convertStringToDate(it.publishedAt)}


