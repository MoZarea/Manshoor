package com.example.gemipost.ui.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gemipost.data.auth.repository.AuthenticationRepository
import com.example.gemipost.ui.auth.util.AuthError
import com.gp.socialapp.util.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SplashViewModel(
    private val authRepo: AuthenticationRepository
): ViewModel() {
    private val _uiState = MutableStateFlow(SplashUiState())
    val uiState = _uiState.asStateFlow()
    init{
        getSignedInUser()
    }
    private fun getSignedInUser() {
        viewModelScope.launch(Dispatchers.IO) {
            authRepo.getSignedInUser().let { result ->
                when (result) {
                    is Result.Success -> {
                        println("getSignedInUser: ${result.data}")
                        _uiState.update {
                            it.copy(
                                isSignedIn = true ,
                            )
                        }
                    }

                    is Result.Error -> {
                        _uiState.update {
                            it.copy(
                                isSignedIn = false
                            )
                        }
                    }

                    else -> Unit
                }
            }
        }
    }
}