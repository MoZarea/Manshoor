package com.example.gemipost.ui.auth.login

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.gemipost.R
import com.example.gemipost.ui.auth.login.components.AuthButton
import com.example.gemipost.ui.auth.login.components.AuthEmailField
import com.example.gemipost.ui.auth.login.components.AuthPasswordField
import com.example.gemipost.ui.auth.login.components.AuthTextButton
import com.example.gemipost.ui.auth.login.components.LoginSignUpSection
import com.example.gemipost.ui.auth.login.components.OutLinedAuthButton
import com.example.gemipost.ui.auth.login.components.googleSignInOption
import com.example.gemipost.ui.auth.login.components.imagevectors.OAuthProviderIcons
import com.example.gemipost.ui.auth.login.components.imagevectors.oauthprovidericons.Google
import com.example.gemipost.ui.auth.login.components.rememberFirebaseAuthLauncher
import com.example.gemipost.ui.theme.GemiPostTheme
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
        onSignUpClicked = {
            onNavigateToSignUp()
            viewModel.resetState()
        },
        onForgotPasswordClicked = {
            onNavigateToForgotPassword()
            viewModel.resetState()
        },
        onEmailChanged = { viewModel.updateEmail(it) },
        onPasswordChanged = { viewModel.updatePassword(it) },
        onNavigateToFeed = {
            onNavigateToFeed()
            viewModel.resetState()
        }
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
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)

        ) {
            AnimatedVisibility(state.isLoading) {
                LinearProgressIndicator()
            }
            Column(
                Modifier
                    .fillMaxWidth()
                    .weight(5f)
                    .background(
                        brush = Brush.radialGradient(
                            colors = listOf(
                                Color(0xf21e2e3c),
                                Color(0xff162534),
                                Color(0xff0c1c2c),
                            ),
                            center = Offset(0f, 0f),
                            radius = 1000f
                        )
                    ),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = stringResource(id = R.string.Sign_in_to_your_Account),
                    style = MaterialTheme.typography.headlineLarge,
                    modifier = Modifier.padding(start = 16.dp),
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
                Text(text ="Sign in to your account.",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(top = 8.dp, start = 16.dp),
                    color = Color.White
                )
            }
            Spacer(modifier = Modifier.weight(0.25f))
            AuthEmailField(state.email, onEmailChanged, state.actionResult)
            AuthPasswordField(
                state.password,
                onPasswordChanged,
                state.actionResult
            )
            AuthTextButton(
                R.string.forgot_password,
                onForgotPasswordClicked,

                )
            AuthButton(R.string.login_str, onSignIn)
            Box(
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 32.dp),
            ) {
                HorizontalDivider(Modifier.align(Alignment.Center))
                Text(
                    text = stringResource(id = R.string.or),
                    modifier = Modifier
                        .align(Alignment.Center)
                        .background(
                            color = MaterialTheme.colorScheme.background,
                            shape = MaterialTheme.shapes.small,
                        )
                        .padding(horizontal = 8.dp),
                    fontSize = MaterialTheme.typography.bodyLarge.fontSize
                )
            }
            OutLinedAuthButton(
                R.string.continue_with_google, onContinueWithGoogle, OAuthProviderIcons.Google
            )
            Spacer(modifier = Modifier.weight(1f))
            LoginSignUpSection(onSignUpClicked)
        }
    }
}

@Composable
@Preview
@Preview(
    "Dark Theme",
    uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL
)
fun LoginScreenPreview() {
    GemiPostTheme {
        LoginContent(
            onNavigateToFeed = {},
            onContinueWithGoogle = {},
            onSignIn = {},
            state = LoginUiState(),
            onSignUpClicked = {},
            onForgotPasswordClicked = {},
            onEmailChanged = {},
            onPasswordChanged = {},
        )
    }
}