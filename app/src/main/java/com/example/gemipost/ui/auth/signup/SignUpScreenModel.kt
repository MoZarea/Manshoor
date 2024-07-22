package com.example.gemipost.ui.auth.signup

import com.example.gemipost.data.auth.repository.AuthenticationRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class SignUpScreenModel(
    private val authRepo: AuthenticationRepository
)  {
    private val _uiState = MutableStateFlow(SignUpUiState())
    val uiState = _uiState.asStateFlow()

}