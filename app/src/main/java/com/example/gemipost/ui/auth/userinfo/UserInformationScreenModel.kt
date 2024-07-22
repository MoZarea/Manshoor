package com.example.gemipost.ui.auth.userinfo

import com.example.gemipost.data.auth.repository.AuthenticationRepository
import com.example.gemipost.data.auth.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class UserInformationScreenModel(
    private val userRepo: UserRepository,
    private val authRepo: AuthenticationRepository
)  {
    private val _uiState = MutableStateFlow(UserInformationUiState())
    val uiState = _uiState.asStateFlow()

}