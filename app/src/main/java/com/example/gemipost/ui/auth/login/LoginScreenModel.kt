package com.example.gemipost.ui.auth.login

import com.example.gemipost.data.auth.repository.AuthenticationRepository
import com.example.gemipost.data.auth.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class LoginScreenModel(
    private val authRepo: AuthenticationRepository,
    private val userRepo: UserRepository,
) {
    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState = _uiState.asStateFlow()
}