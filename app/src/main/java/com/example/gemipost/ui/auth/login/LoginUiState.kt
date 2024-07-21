package com.example.gemipost.ui.auth.login

import com.example.gemipost.data.auth.source.remote.model.User
import com.example.gemipost.ui.auth.util.AuthError


data class LoginUiState(
    var email: String = "",
    var password: String = "",
    var error: AuthError = AuthError.NoError,
    var signedInUser: User? = null,
    var userId: String = "",
    val theme: String = "System Default",
    var isDone: Boolean = false,
)
