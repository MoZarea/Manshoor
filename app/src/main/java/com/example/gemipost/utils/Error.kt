package com.gp.socialapp.util

import kotlinx.serialization.Serializable

@Serializable
sealed interface Error


@Serializable
enum class AssignmentError(val userMessage: String) : Error {
    SERVER_ERROR("Server error"),
}

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
    SERVER_ERROR("Server error"),
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


