package com.example.gemipost.ui.auth.login.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import androidx.compose.ui.unit.sp
import com.example.gemipost.R
import com.example.gemipost.utils.AuthResults
import com.example.gemipost.utils.Error
import com.example.gemipost.utils.userMessage

@Composable
fun AuthPasswordField(
    password: String,
    onPasswordChange: (String) -> Unit,
    error: Error
) {
    var showPassword by remember { mutableStateOf(false) }
    val passwordVisualTransformation = remember { PasswordVisualTransformation() }
    OutlinedTextField(value = password,
        onValueChange = {
            onPasswordChange(it)
        },
        visualTransformation = if (showPassword) {
            VisualTransformation.None
        } else {
            passwordVisualTransformation
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp),
        label = { Text(text = stringResource(R.string.password)) },
        leadingIcon = { Icon(Icons.Filled.Lock, contentDescription = null) },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        trailingIcon = {
            Icon(
                if (showPassword) {
                    Icons.Filled.Visibility
                } else {
                    Icons.Filled.VisibilityOff
                },
                contentDescription = "Toggle password visibility",
                modifier = Modifier.clickable { showPassword = !showPassword })
        },
        isError = error == AuthResults.INVALID_PASSWORD,
        supportingText = {
            if (error == AuthResults.INVALID_PASSWORD) {
                Text(
                    text = error.userMessage(),
                    color = MaterialTheme.colorScheme.error,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(start = 16.dp),
                )
            }
        },
                colors = OutlinedTextFieldDefaults.colors(
            unfocusedContainerColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f),
            unfocusedBorderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f),
        )
    )
}