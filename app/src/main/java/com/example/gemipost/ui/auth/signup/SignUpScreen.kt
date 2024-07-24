package com.example.gemipost.ui.auth.signup

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.rounded.Create
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.gemipost.R
import com.example.gemipost.ui.auth.login.components.AuthEmailField
import com.example.gemipost.ui.auth.login.components.AuthPasswordField
import com.example.gemipost.ui.auth.signup.components.SignUpAvatarSection
import com.example.gemipost.ui.auth.signup.components.SignUpRePasswordField
import com.example.gemipost.ui.auth.signup.components.SignUpHeader
import com.example.gemipost.ui.auth.signup.components.SignUpNameSection
import com.example.gemipost.ui.auth.util.AuthError
import kotlinx.coroutines.launch

@Composable
fun SignUpScreen(
    viewModel: SignUpViewModel = viewModel(),
    onNavigateToFeed: () -> Unit,
    onBackPressed: () -> Unit,
) {
    val state by viewModel.uiState.collectAsState()
    if (state.signedUpUser != null) {
        onNavigateToFeed()
    }
    SignUpContent(
        error = state.error,
        avatarByteArray = state.avatarByteArray,
        onChangeAvatarClicked = {/*TODO*/},
        name = state.name,
        onNameChanged = {viewModel.onNameChange(it)},
        email = state.email,
        onEmailChanged = { viewModel.onEmailChange(it) },
        password = state.password,
        onPasswordChanged = { viewModel.onPasswordChange(it) },
        rePassword = state.rePassword,
        onRePasswordChanged = { viewModel.rePasswordChange(it) },
        onNavigateToLoginScreen = { onBackPressed() },
        onCreateAccount = { viewModel.onSignUp() }
    )
}

@Composable
private fun SignUpContent(
    error: AuthError,
    avatarByteArray: ByteArray,
    onChangeAvatarClicked: () -> Unit,
    name: String,
    onNameChanged: (String) -> Unit,
    email: String,
    onEmailChanged: (String) -> Unit,
    password: String,
    onPasswordChanged: (String) -> Unit,
    rePassword: String,
    onRePasswordChanged: (String) -> Unit,
    onNavigateToLoginScreen: () -> Unit,
    onCreateAccount: () -> Unit,
) {
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        modifier = Modifier.fillMaxSize(),
    ) {
        if (error is AuthError.ServerError) {
            LaunchedEffect(key1 = error) {
                scope.launch {
                    snackbarHostState.showSnackbar(error.message)
                }
            }
        }
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
                .widthIn(max = 600.dp)
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
        ) {
            var passwordVisible by remember { mutableStateOf(false) }
            SignUpHeader()
            Spacer(modifier = Modifier.height(16.dp))
            SignUpAvatarSection(avatarByteArray, onChangeAvatarClicked)
            SignUpNameSection(name, onNameChanged, error)
            AuthEmailField(email = email, onEmailChange = onEmailChanged, error = error)
            AuthPasswordField(
                password = password,
                onPasswordChange = onPasswordChanged,
                passwordVisible = passwordVisible,
                error = error
            )
            SignUpRePasswordField(rePassword, onRePasswordChanged, error, passwordVisible)
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

