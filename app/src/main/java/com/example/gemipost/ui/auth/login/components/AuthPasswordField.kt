package com.example.gemipost.ui.auth.login.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
    passwordVisible: Boolean,
    error: Error
) {
    var password1 = password
    var passwordVisible1 = passwordVisible
    OutlinedTextField(value = password1,
        onValueChange = {
            password1 = it
            onPasswordChange(it)
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp),
        label = { Text(text = stringResource(R.string.password)) },
        leadingIcon = { Icon(Icons.Filled.Lock, contentDescription = null) },
        visualTransformation = if (passwordVisible1) VisualTransformation.None else PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        trailingIcon = {
            val image = if (passwordVisible1) Icons.Filled.Visibility
            else Icons.Filled.VisibilityOff
            val description =
                stringResource(if (passwordVisible1) R.string.hide_password else R.string.show_password)
            IconButton(onClick = { passwordVisible1 = !passwordVisible1 }) {
                Icon(imageVector = image, description)
            }
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
        }
    )
}