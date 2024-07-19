package com.example.gemipost.data.post.source.remote.model

@kotlinx.serialization.Serializable
data class PostAttachment(
    val file: ByteArray = byteArrayOf(),
    val url: String = "",
    val name: String = "",
    val type: String = "",
    val size: Long = 0
) {
    companion object {
        fun PostAttachment.toDbString(): String {
            return "$url|$name|$type|$size"
        }

        fun String.toPostFile(): PostAttachment {
            val parts = this.split("|")
            require(parts.size == 4) { "Invalid string format for PostFile" }
            return PostAttachment(
                url = parts[0],
                name = parts[1],
                type = parts[2],
                size = parts[3].toLong()
            )
        }
    }
}

