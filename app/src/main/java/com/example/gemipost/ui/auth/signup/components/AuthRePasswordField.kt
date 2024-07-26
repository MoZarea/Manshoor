package com.example.gemipost.ui.auth.signup.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
    passwordVisible: Boolean
) {
    var passwordVisible1 = passwordVisible
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
        visualTransformation = if (passwordVisible1) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = {
            val image =
                if (passwordVisible1) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
            val description =
                stringResource(if (passwordVisible1) R.string.hide_password else R.string.show_password)
            IconButton(onClick = { passwordVisible1 = !passwordVisible1 }) {
                Icon(imageVector = image, description)
            }
        }
    )
}