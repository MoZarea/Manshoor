package com.gp.socialapp.util

import kotlinx.serialization.Serializable

@Serializable
sealed interface Error


@Serializable
enum class AssignmentError(val userMessage: String) : Error {
    SERVER_ERROR("Server error"),
}
data class ErrorMessage(val userMessage: String) : Error
@Serializable
enum class AuthError(val userMessage: String) : Error {
    SERVER_ERROR("Server error"),
}


@Serializable
enum class CalendarError(val userMessage: String) : Error {
    SERVER_ERROR("Server error"),
}

@Serializable
enum class ChatError(val userMessage: String) : Error {
    SERVER_ERROR("Server error"),

}

@Serializable
enum class CommunityError(val userMessage: String) : Error {
    SERVER_ERROR("Server error"),
}

@Serializable
enum class MaterialError(val userMessage: String) : Error {
    SERVER_ERROR("Server error"),
}

@Serializable
enum class PostError(val userMessage: String) : Error {
    NO_ERROR("No error"),
    USER_NOT_FOUND("User not found"),
    SERVER_ERROR("Server error"),
    YOU_ARE_NOT_AUTHORIZED("You are not authorized to perform this action"),
    LOGOUT_FAILED("Logout failed"),
    UPVOATE_FAILED("Upvote failed"),
    DOWNVOTE_FAILED("Downvote failed"),
    DELETE_POST_FAILED("Delete post failed"),
    REPORT_POST_FAILED("Report post failed"),
    SHARE_POST_IS_NOT_AVAILABLE("Share post is not available"),
}

@Serializable
enum class ReplyError(val userMessage: String) : Error {
    SERVER_ERROR("Server error"),
}


@Serializable
enum class UserError (val userMessage: String): Error {
    SERVER_ERROR("Server error"),
}

@Serializable
enum class GradesError(val userMessage: String) : Error {
    SERVER_ERROR("Server error"),
}


