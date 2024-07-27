package com.example.gemipost.ui.auth.signup.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.gemipost.R
import com.example.gemipost.utils.AuthResults
import com.example.gemipost.utils.Error
import com.example.gemipost.utils.userMessage

@Composable
fun SignUpRePasswordField(
    rePassword: String,
    onRePasswordChange: (String) -> Unit,
    error: Error,
) {
    var showPassword by remember { mutableStateOf(false) }
    val passwordVisualTransformation = remember { PasswordVisualTransformation() }

    OutlinedTextField(
        value = rePassword,
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
        isError = error == AuthResults.PASSWORD_DOES_NOT_MATCH,
        supportingText = {
            if (error == AuthResults.PASSWORD_DOES_NOT_MATCH) {
                Text(text = error.userMessage())
            }
        },
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        visualTransformation = if (showPassword) {
            VisualTransformation.None
        } else {
            passwordVisualTransformation
        }, trailingIcon = {
            Icon(
                if (showPassword) {
                    Icons.Filled.Visibility
                } else {
                    Icons.Filled.VisibilityOff
                },
                contentDescription = "Toggle password visibility",
                modifier = Modifier.clickable { showPassword = !showPassword })
        }
    )
}