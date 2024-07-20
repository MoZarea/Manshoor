package com.example.gemipost.ui.auth.login.components

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun OAuthProviderItem(
    modifier: Modifier = Modifier,
    provider: MyOAuthProvider,
    onClick: () -> Unit,
    isEnabled: Boolean,
) {
    IconButton(
        modifier = modifier,
        enabled = isEnabled,
        onClick = {
        onClick()
    }) {
        Icon(
            tint = Color.Unspecified,
            imageVector = provider.icon,
            contentDescription = provider.name,
            modifier = Modifier.size(40.dp)
        )
    }
}

data class MyOAuthProvider(
    val name: String, val icon: ImageVector
)