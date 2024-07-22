package com.example.gemipost.ui.auth.util

import androidx.annotation.StringRes

sealed class AuthError {
    data class EmailError(@StringRes var messageId: Int) : AuthError()
    data class PasswordError(@StringRes var messageId: Int) : AuthError()
    data class RePasswordError(@StringRes var messageId: Int) : AuthError()
    data class ServerError(var message: String) : AuthError()
    data class FirstNameError(@StringRes var messageId: Int) : AuthError()
    data class LastNameError(@StringRes var messageId: Int) : AuthError()
    data class PhoneNumberError(@StringRes var messageId: Int) : AuthError()
    data class BirthDateError(@StringRes var messageId: Int) : AuthError()
    data object NoError : AuthError()
}