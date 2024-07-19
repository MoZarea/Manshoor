package com.gp.socialapp.util

import androidx.compose.runtime.Composable
import com.mohamedrejeb.calf.core.PlatformContext

expect fun getPlatform(): Platform

expect fun PlatformContext.copyToClipboard(text: String)
enum class Platform {
    ANDROID, JVM, JS
}