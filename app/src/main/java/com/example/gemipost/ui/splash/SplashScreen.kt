package com.example.gemipost.ui.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.gemipost.R
import org.koin.androidx.compose.koinViewModel

@Composable
fun SplashScreen(
    viewModel: SplashViewModel = koinViewModel(),
    onNavigateToFeed: () -> Unit,
    onNavigateToLogin: () -> Unit
) {
    val state by viewModel.uiState.collectAsState()
    LaunchedEffect(key1 = state.isSignedIn){
        if(state.isSignedIn == true) {
            onNavigateToFeed()
        } else if (state.isSignedIn == false) {
            onNavigateToLogin()
        }
    }
    MaterialTheme {
    Column(
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.onPrimaryContainer),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
    val systemIsDark = isSystemInDarkTheme()
    val isDarkState by remember { mutableStateOf(systemIsDark) }
        Image(
            painter = painterResource(id = if(isDarkState) R.drawable.ic_edulink_dark else R.drawable.ic_edulink_light),
            contentDescription = "Logo",
            modifier = Modifier.size(200.dp)
        )
    }
}}