package com.example.gemipost.ui.auth.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gemipost.data.auth.repository.AuthenticationRepository
import com.example.gemipost.data.auth.source.remote.model.User
import com.example.gemipost.ui.auth.util.Validator
import com.example.gemipost.utils.AuthResults
import com.google.firebase.auth.AuthResult
import com.gp.socialapp.util.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel(
    private val authRepo: AuthenticationRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState = _uiState.asStateFlow()
    private fun getSignedInUser() {
        viewModelScope.launch(Dispatchers.IO) {
            authRepo.getSignedInUser().let { result ->
                when (result) {
                    is Result.Success -> {
                        updateUser(result.data)
                        updateActionResult(AuthResults.LOGIN_SUCCESS)
                    }
                    is Result.Error -> {
                        updateActionResult(result.message)
                    }
                    is Result.Loading -> {
                        enableLoading()
                    }
                }
            }
        }
    }

    private fun updateActionResult(message: AuthResults) {
        println("zarea:updateActionResult: $message")
        _uiState.update {
            it.copy(
                actionResult = message,
                isLoading = false
            )
        }
    }

    private fun updateUser(data: User) {
        println("zarea:updateUser: $data")
        _uiState.update {
            it.copy(
                signedInUser = data,
                isLoading = false
            )
        }
    }

    fun onSignIn() {
        with(_uiState.value) {
            if (!Validator.EmailValidator.validateAll(email)) {
                viewModelScope.launch {
                    updateActionResult(AuthResults.INVALID_EMAIL)
                }
                return
            } else {
                updateActionResult(AuthResults.IDLE)
            }
            if (password.length < 6 || !Validator.PasswordValidator.validateAll(password)) {
                viewModelScope.launch {
                    updateActionResult(AuthResults.INVALID_PASSWORD)
                }
                return
            } else {
                updateActionResult(AuthResults.IDLE)
            }
        }
        viewModelScope.launch {
            with(_uiState.value) {
                authRepo.signInWithEmail(email, password).collect { result ->
                    when (result) {
                        is Result.Success -> {
                            updateUser(result.data)
                            updateActionResult(AuthResults.LOGIN_SUCCESS)
                        }

                        is Result.Error -> {
                            updateActionResult(result.message)
                        }

                        is Result.Loading -> {
                            enableLoading()
                        }
                    }
                }
            }
        }
    }

    private fun enableLoading() {
        println( "zarea:enableLoading: ")
        _uiState.update {
            it.copy(
                isLoading = true
            )
        }
    }

    fun updateEmail(email: String) {
        println("zarea:updateEmail: $email")
        _uiState.update { it.copy(email = email) }
    }

    fun updatePassword(password: String) {
        println("zarea:updatePassword: $password")
        _uiState.update { it.copy(password = password) }
    }
    fun onGoogleSignedIn(result: Result<AuthResult, AuthResults>) {
        result.onSuccessWithData { authResult ->
            updateActionResult(AuthResults.LOGIN_SUCCESS)
            getSignedInUser()
        }.onFailure { error->
            updateActionResult(error)
        }
    }

    fun resetState() {
        _uiState.update { it.copy(actionResult = AuthResults.IDLE) }
    }
}