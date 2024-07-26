package com.example.gemipost.ui.splash

import com.example.gemipost.utils.AuthResults
import com.example.gemipost.utils.Error

data class SplashUiState(
    val actionResult:Error = AuthResults.IDLE,
    val isLoading : Boolean = false,
)
