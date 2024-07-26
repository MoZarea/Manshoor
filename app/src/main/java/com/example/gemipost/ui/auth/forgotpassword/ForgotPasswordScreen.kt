package com.example.gemipost.ui.auth.forgotpassword

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gemipost.R
import com.example.gemipost.utils.AuthResults
import com.example.gemipost.utils.isNotIdle
import com.example.gemipost.utils.userMessage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun ForgotPasswordScreen(
    viewModel: ForgotPasswordViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
    onNavigateToLogin: () -> Unit,
    onBackPressed: () -> Unit,
) {
    val state by viewModel.uiState.collectAsState()
    ForgetPasswordContent(
        state = state,
        onEmailChange = { viewModel.onEmailChange(it) },
        onSendResetEmail = { viewModel.onSendResetEmail() },
        onBackPressed = onBackPressed,
        onNavigateToLogin = onNavigateToLogin,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ForgetPasswordContent(
    modifier: Modifier = Modifier,
    state: ForgotPasswordUiState = ForgotPasswordUiState(),
    onEmailChange: (String) -> Unit = {},
    onSendResetEmail: () -> Unit = {},
    onBackPressed: () -> Unit,
    onNavigateToLogin: () -> Unit,
) {
    val snackbarHostState = remember { SnackbarHostState() }
    LaunchedEffect(state.actionResult) {
        if (state.actionResult == AuthResults.RESET_EMAIL_SENT) {
            launch(Dispatchers.IO) {
                delay(1500)
                onNavigateToLogin()
            }
        } else if (state.actionResult.isNotIdle()) {
            snackbarHostState.showSnackbar(
                message = state.actionResult.userMessage(),
                withDismissAction = true,
            )
        }
    }
    Scaffold(
        snackbarHost = { snackbarHostState },
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(onClick = { onBackPressed() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBackIosNew,
                            contentDescription = "back button"
                        )
                    }
                }
            )
        },
        modifier = modifier
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .widthIn(max = 600.dp)
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
        ) {
            if(state.isLoading) {
                LinearProgressIndicator()
            }
            Text(
                text = stringResource(R.string.reset_your_password),
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentWidth(
                        Alignment.CenterHorizontally
                    ),
                style = MaterialTheme.typography.headlineMedium,
                maxLines = 1,
            )
            OutlinedTextField(
                value = state.email,
                onValueChange = { onEmailChange(it) },
                label = {
                    Text(
                        text = stringResource(R.string.email),
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                singleLine = true,
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Email,
                        contentDescription = null,
                    )
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            )
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 32.dp)
                    .height(50.dp),
                shape = RoundedCornerShape(5.dp),
                onClick = onSendResetEmail,
            ) {
                Icon(imageVector = Icons.AutoMirrored.Filled.Send, contentDescription = null)
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = stringResource(R.string.send_reset_email),
                    fontSize = 16.sp
                )
            }
        }
    }

}