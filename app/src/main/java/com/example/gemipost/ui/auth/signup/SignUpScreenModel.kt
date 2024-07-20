package com.example.gemipost.ui.auth.signup

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.example.gemipost.data.auth.repository.AuthenticationRepository
import com.example.gemipost.ui.auth.util.AuthError
import com.example.gemipost.ui.auth.util.AuthError.EmailError
import com.example.gemipost.ui.auth.util.AuthError.NoError
import com.example.gemipost.ui.auth.util.AuthError.PasswordError
import com.example.gemipost.ui.auth.util.AuthError.RePasswordError
import com.example.gemipost.ui.auth.util.Validator
import com.gp.socialapp.util.Result
import io.github.aakira.napier.Napier
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.getString
import socialmultiplatform.composeapp.generated.resources.Res
import socialmultiplatform.composeapp.generated.resources.invalid_email
import socialmultiplatform.composeapp.generated.resources.invalid_password
import socialmultiplatform.composeapp.generated.resources.passwords_dont_match

class SignUpScreenModel(
    private val authRepo: AuthenticationRepository
) : ScreenModel {
    private val _uiState = MutableStateFlow(SignUpUiState())
    val uiState = _uiState.asStateFlow()
    fun onSignUp() {
        with(_uiState.value) {
            if (!Validator.EmailValidator.validateAll(email)) {
                screenModelScope.launch {
                    val error = EmailError(getString(Res.string.invalid_email))
                    _uiState.value = _uiState.value.copy(error = error)
                }
                return
            } else {
                _uiState.value = _uiState.value.copy(error = NoError)
            }
            if (!Validator.PasswordValidator.validateAll(password)) {
                screenModelScope.launch {
                    val error = PasswordError(getString(Res.string.invalid_password))
                    _uiState.value = _uiState.value.copy(error = error)
                }
                return
            } else {
                _uiState.value = _uiState.value.copy(error = NoError)
            }
            if (rePassword != password) {
                screenModelScope.launch {
                    val error = RePasswordError(getString(Res.string.passwords_dont_match))
                    _uiState.value = _uiState.value.copy(error = error)
                }
                return
            } else {
                _uiState.value = _uiState.value.copy(error = NoError)
            }
        }
        screenModelScope.launch {
            Napier.d("onSignUp: ${uiState.value}")
            with(uiState.value) {
                authRepo.signUpWithEmail(email, password).collect {
                    when (it) {
                        is Result.Success -> {
                            _uiState.value = _uiState.value.copy(
                                error = NoError,
                                signedUpUser = it.data
                            )
                        }

                        is Result.Error -> {
                            Napier.d("onSignUp: Error ${it.message}")
                            _uiState.value = _uiState.value.copy(
                                error = AuthError.ServerError(it.message.userMessage),
                            )
                        }

                        else -> {
                            Napier.d("onSignUp: else")
                        }
                    }
                }
            }
        }
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
}