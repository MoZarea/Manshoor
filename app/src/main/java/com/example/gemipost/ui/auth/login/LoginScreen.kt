package com.example.gemipost.ui.auth.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.LinearProgressIndicator
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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.gemipost.R
import com.example.gemipost.ui.auth.login.components.AuthButton
import com.example.gemipost.ui.auth.login.components.AuthEmailField
import com.example.gemipost.ui.auth.login.components.AuthPasswordField
import com.example.gemipost.ui.auth.login.components.AuthTextButton
import com.example.gemipost.ui.auth.login.components.LoginHeader
import com.example.gemipost.ui.auth.login.components.LoginSignUpSection
import com.example.gemipost.ui.auth.login.components.googleSignInOption
import com.example.gemipost.ui.auth.login.components.imagevectors.OAuthProviderIcons
import com.example.gemipost.ui.auth.login.components.imagevectors.oauthprovidericons.Google
import com.example.gemipost.ui.auth.login.components.rememberFirebaseAuthLauncher
import com.example.gemipost.utils.AuthResults
import com.example.gemipost.utils.isNotIdle
import com.example.gemipost.utils.userMessage
import org.koin.androidx.compose.koinViewModel

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = koinViewModel(),
    onNavigateToFeed: () -> Unit,
    onNavigateToSignUp: () -> Unit,
    onNavigateToForgotPassword: () -> Unit,
) {
    val state by viewModel.uiState.collectAsState()
    val context = LocalContext.current
    val launcher = rememberFirebaseAuthLauncher(
        result = { result ->
            viewModel.onGoogleSignedIn(result)
        },
    )
    val googleSignInClient = googleSignInOption(context)
    LoginContent(
        onContinueWithGoogle = { launcher.launch(googleSignInClient.signInIntent) },
        onSignIn = { viewModel.onSignIn() },
        state = state,
        onSignUpClicked = { onNavigateToSignUp() },
        onForgotPasswordClicked = { onNavigateToForgotPassword() },
        onEmailChanged = { viewModel.updateEmail(it) },
        onPasswordChanged = { viewModel.updatePassword(it) },
        onNavigateToFeed = onNavigateToFeed,
        )
}


@Composable
private fun LoginContent(
    onNavigateToFeed: () -> Unit,
    onContinueWithGoogle: () -> Unit,
    onSignIn: () -> Unit,
    state: LoginUiState,
    onSignUpClicked: () -> Unit,
    onForgotPasswordClicked: () -> Unit,
    onEmailChanged: (String) -> Unit,
    onPasswordChanged: (String) -> Unit,
) {
    val snackbarHostState = remember { SnackbarHostState() }
    LaunchedEffect(state.actionResult) {
        if (state.actionResult == AuthResults.LOGIN_SUCCESS) {
                onNavigateToFeed()
        } else if (state.actionResult.isNotIdle()) {
            snackbarHostState.showSnackbar(
                message = state.actionResult.userMessage(),
                withDismissAction = true,
            )
        }
    }
    var passwordVisible by remember { mutableStateOf(false) }
    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
        modifier = Modifier.fillMaxSize(),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            verticalArrangement = Arrangement.Center,
        ) {
            if (state.isLoading) {
                LinearProgressIndicator()
            }
            LoginHeader()
            AuthEmailField(state.email, onEmailChanged, state.actionResult)
            AuthPasswordField(state.password, onPasswordChanged, passwordVisible, state.actionResult)
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
                R.string.continue_with_google, onContinueWithGoogle, OAuthProviderIcons.Google
            )
            LoginSignUpSection(onSignUpClicked)
        }
    }
}

@Composable
@Preview
fun LoginScreenPreview() {
    LoginScreen(
        onNavigateToFeed = {},
        onNavigateToSignUp = {},
        onNavigateToForgotPassword = {},
    )
}