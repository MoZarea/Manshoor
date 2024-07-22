package com.example.gemipost.ui.auth.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gemipost.R
import com.example.gemipost.data.auth.repository.AuthenticationRepository
import com.example.gemipost.data.auth.repository.UserRepository
import com.example.gemipost.ui.auth.util.AuthError.EmailError
import com.example.gemipost.ui.auth.util.AuthError.NoError
import com.example.gemipost.ui.auth.util.AuthError.PasswordError
import com.example.gemipost.ui.auth.util.AuthError.ServerError
import com.example.gemipost.ui.auth.util.Validator
import com.google.firebase.auth.OAuthProvider
import com.gp.socialapp.util.DispatcherIO
import com.gp.socialapp.util.Result
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel(
    private val authRepo: AuthenticationRepository,
    private val userRepo: UserRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState = _uiState.asStateFlow()

    fun init() {
        getTheme()
        getSignedInUser()
    }


    private fun getTheme() {
        viewModelScope.launch {
            userRepo.getTheme().let { result ->
                when (result) {
                    is Result.Success -> {
                        _uiState.update { it.copy(theme = result.data) }
                    }

                    is Result.Error -> {
                        Log.e("seerde", "getTheme: ${result.message}")
                    }

                    else -> Unit
                }
            }
        }
    }

    private fun getSignedInUser() {
        viewModelScope.launch(DispatcherIO) {
            authRepo.getSignedInUser().let { result ->
                when (result) {
                    is Result.Success -> {
                        _uiState.update {
                            it.copy(
                                signedInUser = result.data,
                                userId = result.data.id,
                            )
                        }
                    }

                    is Result.Error -> {
                        Log.e("seerde", "getSignedInUser: ${result.message}")
                    }

                    else -> Unit
                }
            }
        }
    }

    fun onSignIn() {
        with(_uiState.value) {
            if (!Validator.EmailValidator.validateAll(email)) {
                viewModelScope.launch {
                    val error = EmailError(R.string.invalid_email)
                    _uiState.value = _uiState.value.copy(error = error)
                }
                return
            } else {
                _uiState.value = _uiState.value.copy(error = NoError)
            }
            if (password.length < 6 || !Validator.PasswordValidator.validateAll(password)) {
                viewModelScope.launch {
                    val error = PasswordError(R.string.invalid_password)
                    _uiState.value = _uiState.value.copy(error = error)
                }
                return
            } else {
                _uiState.value = _uiState.value.copy(error = NoError)
            }
        }
        viewModelScope.launch {
            with(_uiState.value) {
                authRepo.signInWithEmail(email, password).collect { result ->
                    when (result) {
                        is Result.Success -> {
                            _uiState.update {
                                it.copy(
                                    userId = result.data.id,
                                    signedInUser = result.data,
                                    error = NoError
                                )
                            }
                        }

                        is Result.Error -> {
                            _uiState.update {
                                it.copy(error = ServerError(result.message.userMessage))
                            }
                        }

                        is Result.Loading -> {
                            Log.d("seerde", "signInWithOAuth: Loading")
                        }

                        else -> Unit
                    }
                }
            }
        }
    }

    fun updateEmail(email: String) {
        _uiState.update { it.copy(email = email) }
    }

    fun updatePassword(password: String) {
        _uiState.update { it.copy(password = password) }
    }

    fun onLogOut() {
        viewModelScope.launch {
            authRepo.logout()
        }
    }

    fun signInWithOAuth(provider: OAuthProvider) {
        viewModelScope.launch(DispatcherIO) {
//            authRepo.signInWithOAuth(provider).collect { result ->
//                when (result) {
//                    is Result.Success -> {
//                        Napier.e("signInWithOAutht: ${result.data}")
//                        _uiState.update {
//                            it.copy(
//                                userId = result.data.id,
//                                signedInUser = result.data,
//                                error = NoError
//                            )
//                        }
//                    }
//
//                    is Result.Error -> {
//                        Napier.e("signInWithOAutht: ${result.message}")
//
//                        _uiState.update {
//                            it.copy(error = ServerError(result.message.userMessage))
//                        }
//                    }
//
//                    is Result.Loading -> {
//                        Napier.d("signInWithOAutht: Loading")
//                    }
//
//                    else -> Unit
//                }
//            }
        }
    }

    fun dispose() {
        _uiState.update {
            it.copy(
                signedInUser = null,
            )
        }
    }
}