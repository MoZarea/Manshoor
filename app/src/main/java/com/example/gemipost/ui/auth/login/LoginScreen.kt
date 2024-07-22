package com.example.gemipost.ui.auth.login

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gemipost.R
import com.example.gemipost.ui.auth.login.components.MyOAuthProvider
import com.example.gemipost.ui.auth.login.components.OAuthProviderItem
import com.example.gemipost.ui.auth.util.AuthError.EmailError
import com.example.gemipost.ui.auth.util.AuthError.PasswordError
import com.example.gemipost.ui.auth.util.AuthError.ServerError
import kotlinx.coroutines.launch


@SuppressLint("CoroutineCreationDuringComposition")
    @OptIn(ExperimentalLayoutApi::class)
    @Composable
    private fun LoginContent(
        oAuthProviders: List<MyOAuthProvider>,
        onSignIn: () -> Unit,
        state: LoginUiState,
        navigateToSignUp: () -> Unit,
        navigateToForgotPassword: () -> Unit,
        onEmailChange: (String) -> Unit,
        onPasswordChange: (String) -> Unit,
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
            if (state.error is ServerError) {
                scope.launch {
                    snackbarHostState.showSnackbar(
                        message = (state.error as ServerError).message,
                    )
                }
            }
            Column(
                modifier = Modifier.fillMaxSize().padding(it),
                verticalArrangement = Arrangement.Center,
            ) {
//            Image(painter = painterResource(resource = Res.drawable.login), contentDescription = null)
                Text(
                    modifier = Modifier.fillMaxWidth()
                        .wrapContentWidth(Alignment.CenterHorizontally),
                    fontSize = 42.sp,
                    text = stringResource(id = R.string.login_str),
                )
                OutlinedTextField(value = email,
                    onValueChange = {
                        email = it
                        onEmailChange(it)
                    },
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp),
                    label = { Text(text = stringResource(R.string.email)) },
                    leadingIcon = { Icon(Icons.Filled.Email, contentDescription = null) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    isError = state.error is EmailError,
                    supportingText = {
                        if (state.error is EmailError) {
                            Text(
                                text = (state.error as EmailError).message,
                                color = MaterialTheme.colorScheme.error,
                                fontSize = 12.sp,
                                modifier = Modifier.padding(start = 16.dp),
                            )
                        }
                    })
                OutlinedTextField(value = password,
                    onValueChange = {
                        password = it
                        onPasswordChange(it)
                    },
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 4.dp),
                    label = { Text(text = stringResource(R.string.password)) },
                    leadingIcon = { Icon(Icons.Filled.Lock, contentDescription = null) },
                    visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    trailingIcon = {
                        val image = if (passwordVisible) Icons.Filled.Visibility
                        else Icons.Filled.VisibilityOff
                        val description =
                            stringResource(if (passwordVisible) R.string.hide_password else R.string.show_password)
                        IconButton(onClick = { passwordVisible = !passwordVisible }) {
                            Icon(imageVector = image, description)
                        }
                    },
                    isError = state.error is PasswordError,
                    supportingText = {
                        if (state.error is PasswordError) {
                            Text(
                                text = (state.error as PasswordError).message,
                                color = MaterialTheme.colorScheme.error,
                                fontSize = 12.sp,
                                modifier = Modifier.padding(start = 16.dp),
                            )
                        }
                    })
                TextButton(
                    onClick = navigateToForgotPassword,
                    enabled = false,
                    modifier = Modifier.padding(start = 16.dp),
                ) {
                    Text(
                        text = stringResource(id = R.string.forgot_password),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        style = TextStyle(textDecoration = TextDecoration.Underline),
                    )
                }
                Button(
                    onClick = onSignIn,
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 4.dp)
                        .height(50.dp),
                    shape = RoundedCornerShape(8.dp),
                ) {
                    Text(
                        text = stringResource(id = R.string.login_str),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                    )
                }
                Text(
                    text = stringResource(R.string.or_login_with),
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 4.dp)
                        .wrapContentWidth(Alignment.CenterHorizontally),
                    fontSize = 16.sp,
                )
                FlowRow(
                    horizontalArrangement = Arrangement.Center,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 4.dp),
                ) {
                    oAuthProviders.forEach { provider ->
                        OAuthProviderItem(
                            modifier = Modifier.padding(4.dp),
                            provider = provider,
                            isEnabled = true,
                        )
                    }
                }
                Row(
                    modifier = Modifier.padding(start = 16.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = stringResource(R.string.dont_have_an_account),
                        fontSize = 16.sp
                    )
                    TextButton(onClick = navigateToSignUp) {
                        Text(
                            text = stringResource(R.string.sign_up),
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            style = TextStyle(textDecoration = TextDecoration.Underline),
                        )
                    }
                }

            }
        }
    }


