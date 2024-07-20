package com.example.gemipost.ui.auth.userinfo

import com.example.gemipost.data.auth.source.remote.model.User
import com.example.gemipost.ui.auth.util.AuthError
import com.gp.socialapp.util.LocalDateTimeUtil.now
import com.gp.socialapp.util.Result
import com.gp.socialapp.util.UserError
import kotlinx.datetime.LocalDateTime

data class UserInformationUiState(
    val signedInUser: User? = null,
    var name: String = "",
    var phoneNumber: String = "",
    var birthDate: LocalDateTime = LocalDateTime.now(),
    var bio: String = "",
    var pfpImageByteArray: ByteArray = byteArrayOf(),
    var error: AuthError = AuthError.NoError,
    val createdState: Result<Unit,UserError> = Result.idle()
)