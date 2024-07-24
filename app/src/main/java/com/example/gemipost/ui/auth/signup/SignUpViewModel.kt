package com.example.gemipost.ui.auth.signup

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gemipost.R
import com.example.gemipost.data.auth.repository.AuthenticationRepository
import com.example.gemipost.ui.auth.util.AuthError
import com.example.gemipost.ui.auth.util.AuthError.EmailError
import com.example.gemipost.ui.auth.util.AuthError.NoError
import com.example.gemipost.ui.auth.util.AuthError.PasswordError
import com.example.gemipost.ui.auth.util.AuthError.RePasswordError
import com.example.gemipost.ui.auth.util.Validator
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
                    val error = EmailError(R.string.invalid_email)
                    _uiState.value = _uiState.value.copy(error = error)
                }
                return
            } else {
                _uiState.value = _uiState.value.copy(error = NoError)
            }
            if (!Validator.PasswordValidator.validateAll(password)) {
                viewModelScope.launch {
                    val error = PasswordError(R.string.invalid_password)
                    _uiState.value = _uiState.value.copy(error = error)
                }
                return
            } else {
                _uiState.value = _uiState.value.copy(error = NoError)
            }
            if (rePassword != password) {
                viewModelScope.launch {
                    val error = RePasswordError(R.string.passwords_dont_match)
                    _uiState.value = _uiState.value.copy(error = error)
                }
                return
            } else {
                _uiState.value = _uiState.value.copy(error = NoError)
            }
            if (!Validator.NameValidator.validateAll(name)) {
                viewModelScope.launch {
                    val error = AuthError.FirstNameError(R.string.invalid_name)
                    _uiState.value = _uiState.value.copy(error = error)
                }
                return
            } else {
                _uiState.value = _uiState.value.copy(error = AuthError.NoError)
            }
        }
        viewModelScope.launch {
            with(uiState.value) {
                authRepo.signUpWithEmail(name, avatarByteArray, email, password).collect {
                    when (it) {
                        is Result.Success -> {
                            _uiState.value = _uiState.value.copy(
                                error = NoError,
                                signedUpUser = it.data
                            )
                        }

                        is Result.Error -> {
                            _uiState.value = _uiState.value.copy(
                                error = AuthError.ServerError(it.message.userMessage),
                            )
                        }

                        else -> {}
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
}