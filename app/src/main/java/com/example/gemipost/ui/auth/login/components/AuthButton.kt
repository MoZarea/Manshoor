package com.example.gemipost.ui.auth.login.components

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AuthButton(
    @StringRes labelId: Int,
    action: () -> Unit,
    startIcon: ImageVector? = null
) {
    Button(
        onClick = action,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp)
            .height(56.dp),

        shape = RoundedCornerShape(8.dp),

    ) {
        if (startIcon != null) {
            Icon(startIcon, contentDescription = null, tint = Color.Unspecified)
        }
        Text(
            text = stringResource(labelId),
            fontSize = 18.sp,
        )
    }
}

@Composable
fun OutLinedAuthButton(
    @StringRes labelId: Int,
    action: () -> Unit,
    startIcon: ImageVector? = null
) {
    OutlinedButton(
        onClick = action,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp)
            .height(56.dp),
        shape = RoundedCornerShape(8.dp),
    ) {
        if (startIcon != null) {
            Icon(
                startIcon,
                contentDescription = null,
                tint = Color.Unspecified,
                modifier = Modifier.size(24.dp),

                )
        }
        Spacer(modifier = Modifier.size(8.dp))
        Text(
            text = stringResource(labelId),
            fontSize = 18.sp,
        )
    }
}