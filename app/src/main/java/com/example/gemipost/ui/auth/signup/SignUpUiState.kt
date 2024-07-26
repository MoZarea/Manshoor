package com.example.gemipost.ui.auth.signup

import android.net.Uri
import com.example.gemipost.data.auth.source.remote.model.User
import com.example.gemipost.utils.AuthResults
import com.example.gemipost.utils.Error

data class SignUpUiState(
    var name: String = "",
    var avatarByteArray: Uri = Uri.EMPTY,
    var email: String = "",
    var password: String = "",
    var rePassword: String = "",
    val signedUpUser: User? = null,
    var actionResult: Error = AuthResults.IDLE,
    var isLoading: Boolean = false,
)
