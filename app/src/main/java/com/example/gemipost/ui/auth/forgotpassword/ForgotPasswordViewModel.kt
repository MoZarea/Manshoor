package com.example.gemipost.ui.auth.forgotpassword

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gemipost.data.auth.repository.AuthenticationRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ForgotPasswordViewModel(
    private val authRepo: AuthenticationRepository
) : ViewModel() {
    val uiState = MutableStateFlow(ForgotPasswordUiState())
    fun onSendResetEmail() {
        viewModelScope.launch {
            val state = authRepo.sendPasswordResetEmail(uiState.value.email)
            state.collect {
                uiState.value = uiState.value.copy(sentState = it)
            }
        }

    }

    fun onEmailChange(email: String) {
        uiState.update { it.copy(email = email) }
    }
}