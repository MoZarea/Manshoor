package com.example.gemipost.data.post.source.local

import com.example.gemipost.data.post.source.remote.model.Post
import com.example.gemipost.data.post.source.remote.model.Tag
import com.example.gemipost.utils.LocalDateTimeUtil.now
import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.ext.toRealmList
import io.realm.kotlin.types.RealmList
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant

class PostEntity() : RealmObject {
    var replyCount: Int = 0
    var authorName: String = ""
    var authorPfp: String = ""
    @PrimaryKey
    var id: String = ""
    var authorID: String = ""
    var createdAt: Long = LocalDateTime.now().toInstant(TimeZone.UTC).toEpochMilliseconds()
    var title: String = ""
    var body: String = ""
    var votes: Int = 0
    var downvoted: RealmList<String> = realmListOf()
    var upvoted: RealmList<String> = realmListOf()
    var tags: RealmList<TagEntity> = realmListOf()
    var attachments: RealmList<String> = realmListOf()
    var moderationStatus: String = ""
}

fun TagEntity.toTag(): Tag {
    return Tag(
        label = label,
        intColor = intColor,
        hexColor = hexColor
    )

}

fun PostEntity.toPost(): Post {
    return Post(
        replyCount = replyCount,
        authorName = authorName,
        authorPfp = authorPfp,
        id = id,
        authorID = authorID,
        createdAt = createdAt,
        title = title,
        body = body,
        votes = votes,
        downvoted = downvoted,
        upvoted = upvoted,
        tags = tags.map { it.toTag() },
        attachments = attachments,
        moderationStatus = moderationStatus
    )
}

fun Post.toPostEntity(): PostEntity {
    return PostEntity().apply {
        replyCount = this@toPostEntity.replyCount
        authorName = this@toPostEntity.authorName
        authorPfp = this@toPostEntity.authorPfp
        id = this@toPostEntity.id
        authorID = this@toPostEntity.authorID
        createdAt = this@toPostEntity.createdAt
        title = this@toPostEntity.title
        body = this@toPostEntity.body
        votes = this@toPostEntity.votes
        downvoted = this@toPostEntity.downvoted.toRealmList()
        upvoted = this@toPostEntity.upvoted.toRealmList()
        tags = this@toPostEntity.tags.map {
            TagEntity().apply {
                label = it.label
                intColor = it.intColor
                hexColor = it.hexColor
            }
        }.toRealmList()
        attachments = this@toPostEntity.attachments.toRealmList()
        moderationStatus = this@toPostEntity.moderationStatus

    }
}

class TagEntity() : RealmObject {

    var label: String = ""
    var intColor: Int = 0
    var hexColor: String = "#000000"
}

class RecentSearchEntity() : RealmObject {
    @PrimaryKey
    var recentSearch: String = ""
}