package com.gp.socialapp.util

import kotlinx.serialization.Serializable

@kotlinx.serialization.Serializable
sealed interface DataSuccess {
    @kotlinx.serialization.Serializable
    enum class Reply(val message: String) : DataSuccess {
        CREATED_SUCCESSFULLY(" Reply Created successfully"),
        UPDATED_SUCCESSFULLY(" Reply Updated successfully"),
        DELETED_SUCCESSFULLY(" Reply Deleted successfully"),
        UPVOTED_SUCCESSFULLY(" Reply Upvoted successfully"),
        DOWNVOTED_SUCCESSFULLY(" Reply Downvoted successfully")
    }

    @Serializable
    enum class User(val message: String) : DataSuccess {
        CREATED_SUCCESSFULLY(" User Created successfully"),
        UPDATED_SUCCESSFULLY(" User Updated successfully"),
        DELETED_SUCCESSFULLY(" User Deleted successfully"),

    }
}