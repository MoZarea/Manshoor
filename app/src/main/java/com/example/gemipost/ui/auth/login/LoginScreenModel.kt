package com.example.gemipost.ui.auth.login

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.example.gemipost.data.auth.repository.AuthenticationRepository
import com.example.gemipost.data.auth.repository.UserRepository
import com.example.gemipost.ui.auth.util.AuthError.EmailError
import com.example.gemipost.ui.auth.util.AuthError.NoError
import com.example.gemipost.ui.auth.util.AuthError.PasswordError
import com.example.gemipost.ui.auth.util.AuthError.ServerError
import com.example.gemipost.ui.auth.util.Validator
import com.gp.socialapp.presentation.settings.components.AppThemeOptions
import com.gp.socialapp.util.DispatcherIO
import com.gp.socialapp.util.Result
import io.github.aakira.napier.Napier
import io.github.jan.supabase.gotrue.providers.OAuthProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.getString
import socialmultiplatform.composeapp.generated.resources.Res
import socialmultiplatform.composeapp.generated.resources.invalid_email
import socialmultiplatform.composeapp.generated.resources.invalid_password

class LoginScreenModel(
    private val authRepo: AuthenticationRepository,
    private val userRepo: UserRepository,
) : ScreenModel {
    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState = _uiState.asStateFlow()

    fun init() {
        getTheme()
        getSignedInUser()
        println("LoginScreenModel init called " + AppThemeOptions.SYSTEM_DEFAULT.value)
    }



    private fun getTheme() {
        screenModelScope.launch {
            userRepo.getTheme().let { result ->
                when (result) {
                    is Result.Success -> {
                        _uiState.update { it.copy(theme = result.data) }
                    }

                    is Result.Error -> {
                        Napier.e("getTheme: ${result.message}")
                    }

                    else -> Unit
                }
            }
        }
    }

    private fun getSignedInUser() {
        screenModelScope.launch(DispatcherIO) {
            authRepo.getSignedInUser().let { result ->
                Napier.e("getSignedInUserAll: $result")
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
                        Napier.e("getSignedInUser: ${result.message}")
                    }

                    else -> Unit
                }
            }
        }
    }

    fun onSignIn() {
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
            if (password.length < 6 || !Validator.PasswordValidator.validateAll(password)) {
                screenModelScope.launch {
                    val error = PasswordError(getString(Res.string.invalid_password))
                    _uiState.value = _uiState.value.copy(error = error)
                }
                return
            } else {
                _uiState.value = _uiState.value.copy(error = NoError)
            }
        }
        screenModelScope.launch {
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
                            Napier.d("signInWithOAuth: Loading")
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
        screenModelScope.launch {
            authRepo.logout()
        }
    }

    fun signInWithOAuth(provider: OAuthProvider) {
        screenModelScope.launch(DispatcherIO) {
            authRepo.signInWithOAuth(provider).collect { result ->
                when (result) {
                    is Result.Success -> {
                        Napier.e("signInWithOAutht: ${result.data}")
                        _uiState.update {
                            it.copy(
                                userId = result.data.id,
                                signedInUser = result.data,
                                error = NoError
                            )
                        }
                    }

                    is Result.Error -> {
                        Napier.e("signInWithOAutht: ${result.message}")

                        _uiState.update {
                            it.copy(error = ServerError(result.message.userMessage))
                        }
                    }

                    is Result.Loading -> {
                        Napier.d("signInWithOAutht: Loading")
                    }

                    else -> Unit
                }
            }
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