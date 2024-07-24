package com.example.gemipost.ui.auth.signup

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gemipost.R
import com.example.gemipost.ui.auth.util.AuthError
import com.example.gemipost.ui.auth.util.AuthError.EmailError
import com.example.gemipost.ui.auth.util.AuthError.PasswordError
import com.example.gemipost.ui.auth.util.AuthError.RePasswordError
import kotlinx.coroutines.launch

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
    private fun SignUpContent(
        state: SignUpUiState,
        onNavigateToLoginScreen: () -> Unit,
        onCreateAccount: () -> Unit,
        onEmailChange: (String) -> Unit,
        onPasswordChange: (String) -> Unit,
        onRePasswordChange: (String) -> Unit,
    ) {
        val scope = rememberCoroutineScope()
        val snackbarHostState = remember { SnackbarHostState() }
        Scaffold(
            snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
            modifier = Modifier.fillMaxSize(),
        ) {
            if (state.error is AuthError.ServerError) {
                scope.launch {
                    snackbarHostState.showSnackbar((state.error as AuthError.ServerError).message)
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
                Text(
                    text = stringResource(R.string.create_account),
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentWidth(Alignment.CenterHorizontally),
                    fontSize = 36.sp,
                )
                OutlinedTextField(
                    value = state.email,
                    onValueChange = { onEmailChange(it) },
                    label = { Text(text = stringResource(R.string.email)) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Filled.Email,
                            contentDescription = null,
                        )
                    },
                    isError = state.error is EmailError,
                    supportingText = {
                        if (state.error is EmailError) {
                            Text(text = (state.error as EmailError).messageId.toString())
                        }
                    },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
                )
                OutlinedTextField(
                    value = state.password,
                    onValueChange = { onPasswordChange(it) },
                    label = { Text(text = stringResource(R.string.password)) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Filled.Lock,
                            contentDescription = null,
                        )
                    },
                    isError = state.error is PasswordError,
                    supportingText = {
                        if (state.error is PasswordError) {
                            Text(text = (state.error as PasswordError).toString())
                        }
                    },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        val image =
                            if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                        val description =
                            stringResource(if (passwordVisible) R.string.hide_password else R.string.show_password)
                        IconButton(onClick = { passwordVisible = !passwordVisible }) {
                            Icon(imageVector = image, description)
                        }
                    }
                )
                OutlinedTextField(
                    value = state.rePassword,
                    onValueChange = { onRePasswordChange(it) },
                    label = { Text(text = stringResource(R.string.retype_password)) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Filled.Lock,
                            contentDescription = null,
                        )
                    },
                    isError = state.error is RePasswordError,
                    supportingText = {
                        if (state.error is RePasswordError) {
                            Text(text = (state.error as RePasswordError).toString())
                        }
                    },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        val image =
                            if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                        val description =
                            stringResource(if (passwordVisible) R.string.hide_password else R.string.show_password)
                        IconButton(onClick = { passwordVisible = !passwordVisible }) {
                            Icon(imageVector = image, description)
                        }
                    }
                )
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
