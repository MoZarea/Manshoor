package com.example.gemipost.ui.auth.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.gemipost.R
import com.example.gemipost.ui.auth.login.components.AuthButton
import com.example.gemipost.ui.auth.login.components.AuthEmailField
import com.example.gemipost.ui.auth.login.components.AuthPasswordField
import com.example.gemipost.ui.auth.login.components.AuthTextButton
import com.example.gemipost.ui.auth.login.components.LoginHeader
import com.example.gemipost.ui.auth.login.components.LoginSignUpSection
import com.example.gemipost.ui.auth.login.components.SplashScreen
import com.example.gemipost.ui.auth.login.components.imagevectors.OAuthProviderIcons
import com.example.gemipost.ui.auth.login.components.imagevectors.oauthprovidericons.Google
import com.example.gemipost.ui.auth.util.AuthError
import com.example.gemipost.ui.auth.util.AuthError.ServerError
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = viewModel(),
    onNavigateToFeed: () -> Unit,
    onNavigateToSignUp: () -> Unit,
    onNavigateToForgotPassword: () -> Unit,
) {
    val state by viewModel.uiState.collectAsState()
    var isSplashVisible by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    LaunchedEffect(key1 = true) {
        isSplashVisible = true
        viewModel.init()
    }

    if (isSplashVisible) {
        SplashScreen()
        LaunchedEffect(key1 = true) {
            scope.launch {
                delay(3000)
                isSplashVisible = false
            }
        }
    } else {
        if (state.signedInUser != null) {
            onNavigateToFeed()
        } else {
            LoginContent(
                onContinueWithGoogle = {/*TODO*/ },
                onSignIn = { viewModel.onSignIn() },
                error = state.error,
                onSignUpClicked = { onNavigateToSignUp() },
                onForgotPasswordClicked = { onNavigateToForgotPassword() },
                onEmailChanged = { viewModel.updateEmail(it) },
                onPasswordChanged = { viewModel.updatePassword(it) },
            )
        }
    }
}


@Composable
private fun LoginContent(
    onContinueWithGoogle: () -> Unit,
    onSignIn: () -> Unit,
    error: AuthError,
    onSignUpClicked: () -> Unit,
    onForgotPasswordClicked: () -> Unit,
    onEmailChanged: (String) -> Unit,
    onPasswordChanged: (String) -> Unit,
) {
    var passwordVisible by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    var password by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
        modifier = Modifier.fillMaxSize(),
    ) {
        LaunchedEffect(error) {
            scope.launch {
                snackbarHostState.showSnackbar(
                    message = (error as ServerError).message,
                )
            }
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            verticalArrangement = Arrangement.Center,
        ) {
            LoginHeader()
            AuthEmailField(email, onEmailChanged, error)
            AuthPasswordField(password, onPasswordChanged, passwordVisible, error)
            AuthTextButton(R.string.forgot_password, onForgotPasswordClicked)
            Box(
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 4.dp),
            ) {
                HorizontalDivider(Modifier.align(Alignment.Center))
                Text(text = stringResource(id = R.string.or), Modifier.align(Alignment.Center))
            }
            AuthButton(R.string.login_str, onSignIn)
            AuthButton(
                R.string.continue_with_google,
                onContinueWithGoogle,
                OAuthProviderIcons.Google
            )
            LoginSignUpSection(onSignUpClicked)
        }
    }
}