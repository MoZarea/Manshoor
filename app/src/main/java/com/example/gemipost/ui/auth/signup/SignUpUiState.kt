package com.example.gemipost.ui.auth.signup

import com.example.gemipost.data.auth.source.remote.model.User
import com.example.gemipost.ui.auth.util.AuthError

data class SignUpUiState(
    var name: String = "",
    var avatarByteArray: ByteArray = byteArrayOf(),
    var email: String = "",
    var password: String = "",
    var rePassword: String = "",
    val signedUpUser: User? = null,
    var error: AuthError = AuthError.NoError,
)
