package com.example.gemipost.ui.auth.login.components

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AuthTextButton(
    @StringRes textId: Int,
    action: () -> Unit
) {
    TextButton(
        onClick = action,
        modifier = Modifier.padding(start = 16.dp),
    ) {
        Text(
            text = stringResource(textId),
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            style = TextStyle(textDecoration = TextDecoration.Underline),
        )
    }
}
