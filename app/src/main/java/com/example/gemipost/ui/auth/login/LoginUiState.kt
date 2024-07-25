package com.example.gemipost.ui.auth.login

import com.example.gemipost.data.auth.source.remote.model.User
import com.example.gemipost.ui.auth.util.AuthError
import com.example.gemipost.utils.Status


data class LoginUiState(
    var email: String = "",
    var password: String = "",
    var error: AuthError = AuthError.NoError,
    var signedInUser: User? = null,
)
