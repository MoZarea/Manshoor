package com.example.gemipost.ui.auth.login.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gemipost.R

@Composable
fun LoginSignUpSection(navigateToSignUp: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(start = 16.dp),
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
//                style = TextStyle(textDecoration = TextDecoration.Underline),
            )
        }
    }
}
