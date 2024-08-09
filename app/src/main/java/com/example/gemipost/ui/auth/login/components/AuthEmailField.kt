package com.example.gemipost.ui.auth.login.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gemipost.R
import com.example.gemipost.ui.auth.util.AuthError
import com.example.gemipost.ui.theme.GemiPostTheme
import com.example.gemipost.utils.AuthResults
import com.example.gemipost.utils.Error
import com.example.gemipost.utils.userMessage

@Composable
fun AuthEmailField(
    email: String,
    onEmailChange: (String) -> Unit,
    error: Error,
) {
    OutlinedTextField(
        value = email,
        onValueChange = {
            onEmailChange(it)
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        label = { Text(text = stringResource(R.string.email)) },
        leadingIcon = { Icon(Icons.Filled.Email, contentDescription = null) },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        isError = error == AuthResults.INVALID_EMAIL,
        supportingText = {
            if (error == AuthResults.INVALID_EMAIL) {
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
@Composable
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL, showBackground = true,
    showSystemUi = true
)
@Preview(showBackground = true, showSystemUi = true)
fun AuthEmailFieldPreview() {
    GemiPostTheme {
        AuthEmailField(
            email = "",
            onEmailChange = {},
            error = AuthResults.IDLE
        )
    }
}
