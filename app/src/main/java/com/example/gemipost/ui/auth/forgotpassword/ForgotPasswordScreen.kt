package com.gp.socialapp.presentation.auth.passwordreset

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
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gemipost.R
import com.example.gemipost.ui.auth.forgotpassword.ForgotPasswordUiState
import com.example.gemipost.ui.auth.forgotpassword.ForgotPasswordViewModel

@Composable
fun ForgotPasswordScreen(
    viewModel: ForgotPasswordViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val state by viewModel.uiState.collectAsState()
    Scaffold { paddingValues ->
        ForgetPasswordContent(
            modifier = Modifier.padding(paddingValues),
            state = state,
            onEmailChange = { viewModel.onEmailChange(it) },
            onSendResetEmail = { /*todo*/ }
        )
    }
}

@Composable
private fun ForgetPasswordContent(
    modifier: Modifier = Modifier,
    state: ForgotPasswordUiState = ForgotPasswordUiState(),
    onEmailChange: (String) -> Unit = {},
    onSendResetEmail: () -> Unit = {}
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .widthIn(max = 600.dp)
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
    ) {
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