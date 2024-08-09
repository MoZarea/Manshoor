package com.example.gemipost.ui.auth.signup.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gemipost.R
import com.example.gemipost.utils.AuthResults
import com.example.gemipost.utils.Error
import com.example.gemipost.utils.userMessage

@Composable
fun SignUpNameSection(
    name: String,
    onNameChanged: (String) -> Unit,
    error: Error
) {
    OutlinedTextField(
        value = name,
        onValueChange = onNameChanged,
        label = { Text(text = stringResource(R.string.name)) },
        isError = error == AuthResults.INVALID_NAME,
        supportingText = {
            if (error == AuthResults.INVALID_NAME) {
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
        ),
                modifier = Modifier
                . fillMaxWidth ()
            .padding(horizontal = 16.dp, vertical = 8.dp),
    )
}

