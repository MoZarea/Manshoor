package com.example.gemipost.ui.auth.userinfo

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.example.gemipost.data.auth.repository.AuthenticationRepository
import com.example.gemipost.data.auth.repository.UserRepository
import com.example.gemipost.data.auth.source.remote.model.User
import com.example.gemipost.ui.auth.util.AuthError
import com.example.gemipost.ui.auth.util.Validator
import com.gp.socialapp.util.DispatcherIO
import com.gp.socialapp.util.LocalDateTimeUtil.toMillis
import com.gp.socialapp.util.Result
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDateTime
import org.jetbrains.compose.resources.getString
import socialmultiplatform.composeapp.generated.resources.Res
import socialmultiplatform.composeapp.generated.resources.invalid_name
import socialmultiplatform.composeapp.generated.resources.invalid_phone_number
import socialmultiplatform.composeapp.generated.resources.user_must_be_at_least_18_years_old

class UserInformationScreenModel(
    private val userRepo: UserRepository,
    private val authRepo: AuthenticationRepository
) : ScreenModel {
    private val _uiState = MutableStateFlow(UserInformationUiState())
    val uiState = _uiState.asStateFlow()
    fun onCompleteAccount() {
        with(_uiState.value) {
            if (!Validator.NameValidator.validateAll(name)) {
                screenModelScope.launch {
                    val error = AuthError.FirstNameError(getString(Res.string.invalid_name))
                    _uiState.value = _uiState.value.copy(error = error)
                }
                return
            } else {
                _uiState.value = _uiState.value.copy(error = AuthError.NoError)
            }
            if (!Validator.PhoneNumberValidator.validateAll(phoneNumber)) {
                screenModelScope.launch {
                    val error =
                        AuthError.PhoneNumberError(getString(Res.string.invalid_phone_number))
                    _uiState.value = _uiState.value.copy(error = error)
                }
                return
            } else {
                _uiState.value = _uiState.value.copy(error = AuthError.NoError)
            }
            if (!Validator.BirthDateValidator.validateAll(birthDate)) {
                screenModelScope.launch {
                    val error =
                        AuthError.BirthDateError(getString(Res.string.user_must_be_at_least_18_years_old))
                    _uiState.value = _uiState.value.copy(error = error)
                }
                return
            } else {
                _uiState.value = _uiState.value.copy(error = AuthError.NoError)
            }
        }
        screenModelScope.launch {
            with(uiState.value) {
                val user = User(
                    id = signedInUser?.id ?: "",
                    email = signedInUser?.email ?: "",
                    name = name,
                    phoneNumber = phoneNumber,
                    birthdate = birthDate.toMillis(),
                    bio = bio,
                    createdAt = signedInUser?.createdAt ?: 0L,
                    isDataComplete = true
                )
                val result = userRepo.updateUserInfo(user, pfpImageByteArray)
                if (result is Result.Success) {
                    getSignedInUser()
                } else if (result is Result.Error) {
                    _uiState.update { it.copy(error = AuthError.ServerError(result.message.userMessage)) }
                }
            }
        }
    }

    private fun getSignedInUser() {
        screenModelScope.launch(DispatcherIO) {
            authRepo.getSignedInUser().let { result ->
                if (result is Result.Success) {
                    println("User: ${result.data}")
                    userRepo.createRemoteUser(result.data).let { result2 ->
                        when (result2) {
                            is Result.Error -> _uiState.update { state ->
                                state.copy(
                                    error = AuthError.ServerError(
                                        result2.message.userMessage
                                    )
                                )
                            }

                            is Result.Success -> _uiState.update { state ->
                                state.copy(
                                    signedInUser = result.data,
                                    createdState = Result.Success(Unit)
                                )
                            }

                            Result.Loading -> Unit
                        }
                    }
                } else if (result is Result.Error) {
                    _uiState.update { state -> state.copy(error = AuthError.ServerError(result.message.userMessage)) }
                }
            }
        }
    }

    fun onNameChange(name: String) {
        _uiState.update { it.copy(name = name) }
    }

    fun onPhoneNumberChange(phoneNumber: String) {
        _uiState.update { it.copy(phoneNumber = phoneNumber) }
    }

    fun onBirthDateChange(birthDate: LocalDateTime) {
        _uiState.update { it.copy(birthDate = birthDate) }
    }

    fun onBioChange(bio: String) {
        _uiState.update { it.copy(bio = bio) }
    }

    fun onImageChange(image: ByteArray) {
        _uiState.update { it.copy(pfpImageByteArray = image) }
    }

    fun onScreenStart(signedInUser: User) {
        _uiState.update { it.copy(signedInUser = signedInUser) }
    }

    override fun onDispose() {
        super.onDispose()
        _uiState.value = UserInformationUiState()
    }
}