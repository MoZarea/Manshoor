package com.example.gemipost.ui.auth.signup.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gemipost.R
import com.example.gemipost.ui.auth.util.AuthError

@Composable
fun SignUpNameSection(
    name: String,
    onNameChanged: (String) -> Unit,
    error: AuthError
) {
    OutlinedTextField(
        value = name,
        onValueChange = onNameChanged,
        label = { Text(text = stringResource(R.string.name)) },
        isError = error is AuthError.FirstNameError,
        supportingText = {
            if (error is AuthError.FirstNameError) {
                Text(
                    text = stringResource((error as AuthError.FirstNameError).messageId),
                    color = MaterialTheme.colorScheme.error,
                    fontSize = 12.sp
                )
            }

        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 4.dp)
    )
}

