package com.example.gemipost.ui.auth.login.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.gemipost.R

@Composable
fun SplashScreen() {
    MaterialTheme {
    Column(
        Modifier.fillMaxSize().background(MaterialTheme.colorScheme.onPrimaryContainer),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
    val systemIsDark = isSystemInDarkTheme()
    val isDarkState by remember { mutableStateOf(systemIsDark) }
        Image(
            painter = painterResource(id = if(isDarkState) R.drawable.ic_edulink_dark else R.drawable.ic_edulink_light),
            contentDescription = "Logo",
            modifier = Modifier.size(200.dp)
        )
    }
}}