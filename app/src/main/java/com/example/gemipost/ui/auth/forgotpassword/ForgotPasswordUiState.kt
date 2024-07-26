package com.example.gemipost.ui.auth.forgotpassword

import com.example.gemipost.utils.AuthResults
import com.example.gemipost.utils.Error

data class ForgotPasswordUiState(
    var email: String = "",
    var actionResult :Error = AuthResults.IDLE,
    var isLoading: Boolean = false
)