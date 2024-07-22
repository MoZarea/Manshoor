package com.example.gemipost.ui.auth.login.components

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
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
            .height(50.dp),
        shape = RoundedCornerShape(8.dp),
    ) {
        if (startIcon != null) {
            Icon(startIcon, contentDescription = null, tint = Color.Unspecified)
        }
        Text(
            text = stringResource(labelId),
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
        )
    }
}