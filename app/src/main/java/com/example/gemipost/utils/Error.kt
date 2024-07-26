package com.example.gemipost.utils

import kotlinx.serialization.Serializable

@Serializable
sealed interface Error

fun Error.userMessage(): String {
    return when (this) {
        is PostResults -> userMessage
        is AuthResults -> userMessage
        is ReplyResults -> userMessage
    }
}

fun Error.isNotIdle(): Boolean {
    return when (this) {
        is PostResults -> this != PostResults.IDLE
        is AuthResults -> this !in listOf(
            AuthResults.IDLE,
            AuthResults.INVALID_EMAIL,
            AuthResults.INVALID_NAME,
            AuthResults.INVALID_PASSWORD,
            AuthResults.PASSWORD_DOES_NOT_MATCH
        )

        is ReplyResults -> this != ReplyResults.IDLE
    }
}


@Serializable
enum class PostResults(val userMessage: String) : Error {
    IDLE(""),
    POST_CREATED("Post Created Successfully"),
    POST_NOT_CREATED("Post Not Created"),
    POST_UPDATED("Post Updated Successfully"),
    POST_NOT_UPDATED("Post Not Updated"),
    POST_DELETED("Post Deleted Successfully"),
    POST_NOT_DELETED("Post Not Deleted"),
    POST_REPORTED("Post Reported Successfully"),
    POST_NOT_REPORTED("Post Not Reported"),
    SERVER_ERROR("Server error"),
    NETWORK_ERROR("Network error"),
    POST_UPVOTE_FAILED("Post upvote failed"),
    POST_DOWNVOTE_FAILED("Post downvote failed"),
    POST_NOT_FOUND("Post not found"),
    POST_FETCHED_FAILED("Post fetched failed"),
    SHARE_POST_IS_NOT_AVAILABLE("Share post is not available"),
    REPORT_POST_IS_NOT_AVAILABLE("Report post is not available"),

}

@Serializable
enum class ReplyResults(val userMessage: String) : Error {
    IDLE(""),
    REPLY_CREATED("Reply Created Successfully"),
    REPLY_NOT_CREATED("Reply Not Created"),
    REPLY_UPDATED("Reply Updated Successfully"),
    REPLY_NOT_UPDATED("Reply Not Updated"),
    REPLY_DELETED("Reply Deleted Successfully"),
    REPLY_NOT_DELETED("Reply Not Deleted"),
    REPLY_REPORTED("Reply Reported Successfully"),
    REPLY_NOT_REPORTED("Reply Not Reported"),
    SERVER_ERROR("Server error"),
    NETWORK_ERROR("Network error"),
    REPLY_UPVOTE_FAILED("Reply upvote failed"),
    REPLY_DOWNVOTE_FAILED("Reply downvote failed"),
    REPLY_NOT_FOUND("Reply not found"),
    REPLY_FETCHED_FAILED("Reply fetched failed"),

}


@Serializable
enum class AuthResults(val userMessage: String) : Error {
    IDLE(""),
    INVALID_NAME("Invalid Name"),
    PASSWORD_DOES_NOT_MATCH("Password does not match"),
    INVALID_EMAIL("Invalid Email"),
    INVALID_PASSWORD("Invalid Password"),
    RESET_EMAIL_SENT("Reset Email Sent"),
    LOGIN_SUCCESS("Login Successful"),
    LOGIN_FAILED("Login Failed"),
    SIGNUP_SUCCESS("Signup Successful"),
    SIGNUP_FAILED("Signup Failed"),
    LOGOUT_SUCCESS("Logout Successful"),
    LOGOUT_FAILED("Logout Failed"),
    SERVER_ERROR("Server error"),
    NETWORK_ERROR("Network error"),
    USER_NOT_FOUND("User not found"),
}


