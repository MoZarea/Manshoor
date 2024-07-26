package com.example.gemipost.ui.auth.signup

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Button
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gemipost.R
import com.example.gemipost.ui.auth.login.components.AuthEmailField
import com.example.gemipost.ui.auth.login.components.AuthPasswordField
import com.example.gemipost.ui.auth.signup.components.SignUpAvatarSection
import com.example.gemipost.ui.auth.signup.components.SignUpHeader
import com.example.gemipost.ui.auth.signup.components.SignUpNameSection
import com.example.gemipost.ui.auth.signup.components.SignUpRePasswordField
import com.example.gemipost.utils.AuthResults
import com.example.gemipost.utils.Error
import com.example.gemipost.utils.isNotIdle
import com.example.gemipost.utils.userMessage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun SignUpScreen(
    viewModel: SignUpViewModel = koinViewModel(),
    onNavigateToFeed: () -> Unit,
    onBackPressed: () -> Unit,
) {
    val state by viewModel.uiState.collectAsState()
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri ->
            uri?.let { selectedUri ->
                viewModel.onImageChange(selectedUri)
            }
        }
    )
    SignUpContent(
        error = state.actionResult,
        avatarByteArray = state.avatarByteArray,
        onChangeAvatarClicked = {
            galleryLauncher.launch(
                PickVisualMediaRequest(
                    mediaType = ActivityResultContracts.PickVisualMedia.ImageOnly
                )
            )
        },
        onNameChanged = { viewModel.onNameChange(it) },
        onEmailChanged = { viewModel.onEmailChange(it) },
        onPasswordChanged = { viewModel.onPasswordChange(it) },
        onRePasswordChanged = { viewModel.rePasswordChange(it) },
        onNavigateToLoginScreen = {
            onBackPressed()
            viewModel.resetState()
                                  },
        onCreateAccount = { viewModel.onSignUp() },
        state = state,
        onNavigateToFeed = {
            onNavigateToFeed()
            viewModel.resetState()
        },
    )
}


@Composable
private fun SignUpContent(
    error: Error,
    avatarByteArray: Uri,
    onChangeAvatarClicked: () -> Unit,
    onNameChanged: (String) -> Unit,
    onEmailChanged: (String) -> Unit,
    onPasswordChanged: (String) -> Unit,
    onRePasswordChanged: (String) -> Unit,
    onNavigateToLoginScreen: () -> Unit,
    onCreateAccount: () -> Unit,
    state: SignUpUiState,
    onNavigateToFeed: () -> Unit,
) {
    val snackbarHostState = remember { SnackbarHostState() }
    LaunchedEffect(state.actionResult) {
        if (state.actionResult == AuthResults.SIGNUP_SUCCESS) {
            launch(Dispatchers.IO) {
                delay(1500)
                onNavigateToFeed()
            }
        } else if (state.actionResult.isNotIdle()) {
            snackbarHostState.showSnackbar(
                message = state.actionResult.userMessage(),
                withDismissAction = true,
            )
        }
    }
    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        modifier = Modifier.fillMaxSize(),
    ) {

        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
                .widthIn(max = 600.dp)
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
        ) {
            if (state.isLoading) {
                LinearProgressIndicator()
            }
            var passwordVisible by remember { mutableStateOf(false) }
            SignUpHeader()
            Spacer(modifier = Modifier.height(16.dp))
            SignUpAvatarSection(avatarByteArray, onChangeAvatarClicked)
            SignUpNameSection(state.name, onNameChanged, error)
            AuthEmailField(email = state.email, onEmailChange = onEmailChanged, error = error)
            AuthPasswordField(
                password = state.password,
                onPasswordChange = onPasswordChanged,
                passwordVisible = passwordVisible,
                error = error
            )
            SignUpRePasswordField(state.rePassword, onRePasswordChanged, error, passwordVisible)
            Button(
                onClick = onCreateAccount,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 32.dp)
                    .height(52.dp),
            ) {
                Text(
                    text = stringResource(R.string.create_account),
                    fontSize = 18.sp
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.already_have_an_account),
                    modifier = Modifier
                        .padding(end = 8.dp),
                    fontSize = 18.sp
                )
                TextButton(
                    onClick = onNavigateToLoginScreen,
                ) {
                    Text(
                        text = stringResource(R.string.login_str),
                        fontSize = 18.sp,
                    )
                }

            }

        }
    }
}

