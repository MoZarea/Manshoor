package com.example.gemipost.ui.auth.signup

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gemipost.data.auth.repository.AuthenticationRepository
import com.example.gemipost.data.auth.source.remote.model.User
import com.example.gemipost.ui.auth.util.Validator
import com.example.gemipost.utils.AuthResults
import com.gp.socialapp.util.Result
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SignUpViewModel(
    private val authRepo: AuthenticationRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(SignUpUiState())
    val uiState = _uiState.asStateFlow()
    fun onSignUp() {
        with(_uiState.value) {
            if (!Validator.EmailValidator.validateAll(email)) {
                viewModelScope.launch {
                    updateActionResult(AuthResults.INVALID_EMAIL)
                }
                return
            } else {
                updateActionResult(AuthResults.IDLE)
            }
            if (!Validator.PasswordValidator.validateAll(password)) {
                viewModelScope.launch {
                    updateActionResult(AuthResults.INVALID_PASSWORD)
                }
                return
            } else {
                updateActionResult(AuthResults.IDLE)
            }
            if (rePassword != password) {
                viewModelScope.launch {
                    updateActionResult(AuthResults.PASSWORD_DOES_NOT_MATCH)
                }
                return
            } else {
                updateActionResult(AuthResults.IDLE)
            }
            if (!Validator.NameValidator.validateAll(name)) {
                viewModelScope.launch {
                    updateActionResult(AuthResults.INVALID_NAME)
                }
                return
            } else {
                updateActionResult(AuthResults.IDLE)
            }
        }
        viewModelScope.launch {
            with(uiState.value) {
                authRepo.signUpWithEmail(name, avatarByteArray, email, password).collect {
                    when (it) {
                        is Result.Success -> {
                            updateUser(it.data)
                            updateActionResult(AuthResults.SIGNUP_SUCCESS)
                        }

                        is Result.Error -> {
                            updateActionResult(it.message)
                        }

                        is Result.Loading -> {
                            enableLoading()
                        }
                    }
                }
            }
        }
    }


    fun onNameChange(name: String) {
        _uiState.update { it.copy(name = name) }
    }

    fun onImageChange(image: Uri) {
        _uiState.update { it.copy(avatarByteArray = image) }
    }

    fun onEmailChange(email: String) {
        _uiState.update { it.copy(email = email) }
    }

    fun onPasswordChange(password: String) {
        _uiState.update { it.copy(password = password) }
    }

    fun rePasswordChange(rePassword: String) {
        _uiState.update { it.copy(rePassword = rePassword) }
    }

    fun despose() {
        _uiState.value = SignUpUiState()
    }

    private fun updateActionResult(message: AuthResults) {
        _uiState.update {
            it.copy(
                actionResult = message,
                isLoading = false
            )
        }
    }

    private fun enableLoading() {
        _uiState.update { it.copy(isLoading = true) }
    }

    private fun updateUser(data: User) {
        _uiState.update {
            it.copy(
                signedUpUser = data,
                isLoading = false
            )
        }
    }
}