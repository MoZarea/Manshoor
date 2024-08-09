package com.example.gemipost.ui.auth.forgotpassword

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gemipost.R
import com.example.gemipost.ui.auth.login.components.AuthEmailField
import com.example.gemipost.utils.AuthResults
import com.example.gemipost.utils.isNotIdle
import com.example.gemipost.utils.userMessage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun ForgotPasswordScreen(
    viewModel: ForgotPasswordViewModel = koinViewModel(),
    onNavigateToLogin: () -> Unit,
    onBackPressed: () -> Unit,
) {
    val state by viewModel.uiState.collectAsState()
    ForgetPasswordContent(
        state = state,
        onEmailChange = { viewModel.onEmailChange(it) },
        onSendResetEmail = { viewModel.onSendResetEmail() },
        onBackPressed = {
            onBackPressed()
            viewModel.resetState()
        },
        onNavigateToLogin = {
            onNavigateToLogin()
            viewModel.resetState()
        },
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
                .padding(paddingValues),
            verticalArrangement = Arrangement.Center,
        ) {
            AnimatedVisibility(state.isLoading) {
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
            AuthEmailField(
                email = state.email,
                onEmailChange = onEmailChange,
                error = state.actionResult
            )

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 4.dp)
                    .height(56.dp),
                shape = RoundedCornerShape(8.dp),
                onClick = onSendResetEmail,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xffc0e863),
                    contentColor = Color.Black
                ),
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