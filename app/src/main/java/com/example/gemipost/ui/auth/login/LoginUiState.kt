package com.example.gemipost.ui.auth.login

import com.example.gemipost.data.auth.source.remote.model.User
import com.example.gemipost.utils.AuthResults
import com.example.gemipost.utils.Error


data class LoginUiState(
    var email: String = "",
    var password: String = "",
    var actionResult: Error = AuthResults.IDLE,
    var signedInUser: User? = null,
    var isLoading: Boolean = false,
)
