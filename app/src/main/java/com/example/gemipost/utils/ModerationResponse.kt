package com.gp.socialapp.util
@kotlinx.serialization.Serializable
enum class ModerationSafety {
    SAFE,
    UNSAFE_TITLE,
    UNSAFE_BODY,
    UNSAFE_IMAGE,
    UNSAFE_MESSAGE,
    UNSAFE_REPLY,
    NOT_FOUND,
    ERROR
}