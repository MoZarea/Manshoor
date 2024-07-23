package com.example.gemipost.ui.auth.forgotpassword

import com.gp.socialapp.util.AuthError
import com.gp.socialapp.util.Result

data class ForgotPasswordUiState(
    var email: String = "",
    var sentState: Result<Unit,AuthError> = Result.idle()
)