package com.example.gemipost.data.post.source.remote.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable
@Parcelize
@Serializable
data class Tag(
    val label: String = "",
    val intColor: Int = 0,
    val hexColor: String = "#000000",
    val communityID: String = ""
) : Parcelable {
    companion object {
        fun Tag.toDbString(): String {
            return "$label|$intColor|$hexColor|$communityID"
        }

        fun String.toTag(): Tag {
            println("this: $this")
            val parts = this.split("|")
            println("parts: $parts")
            require(parts.size == 4) { "Invalid string format for Tag" }
            return Tag(parts[0], parts[1].toInt(), parts[2], parts[3])
        }
    }
}
