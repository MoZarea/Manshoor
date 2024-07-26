package com.example.gemipost.ui.auth.forgotpassword

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gemipost.data.auth.repository.AuthenticationRepository
import com.example.gemipost.utils.AuthResults
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
            state.collect {result->
                result.onSuccessWithData {
                    updateActionResult(it)
                }.onFailure {
                    updateActionResult(it)
                }.onLoading {
                    enableLoading()
                }
            }
        }

    }

    private fun enableLoading() {
        uiState.update { it.copy(isLoading = true) }
    }

    private fun updateActionResult(result: AuthResults) {
        uiState.update { it.copy(actionResult = result, isLoading = false) }
    }

    fun onEmailChange(email: String) {
        uiState.update { it.copy(email = email) }
    }
}