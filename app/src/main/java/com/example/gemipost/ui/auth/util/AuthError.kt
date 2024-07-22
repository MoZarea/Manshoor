package com.example.gemipost.ui.auth.util

sealed class AuthError {
    data class EmailError(var message: String) : AuthError()
    data class PasswordError(var message: String) : AuthError()
    data class RePasswordError(var message: String) : AuthError()
    data class ServerError(var message: String) : AuthError()
    data class FirstNameError(var message: String) : AuthError()
    data class LastNameError(var message: String) : AuthError()
    data class PhoneNumberError(var message: String) : AuthError()
    data class BirthDateError(var message: String) : AuthError()
    data object NoError : AuthError()
}