package com.example.gemipost.ui.auth.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.painterResource
import socialmultiplatform.composeapp.generated.resources.Res
import socialmultiplatform.composeapp.generated.resources.logo
import socialmultiplatform.composeapp.generated.resources.logoDark

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
            painter = painterResource(resource = if(isDarkState) Res.drawable.logoDark else Res.drawable.logo),
            contentDescription = "Logo",
        )

    }
}}