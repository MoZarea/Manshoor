package com.gp.socialapp.util

import androidx.compose.ui.graphics.Color

fun String.toColor(): Color {
    val string = this.removePrefix("#")
    val withAlpha = string.length == 8
    val startIndex = if (withAlpha) 2 else 0
    val red = string.substring(startIndex, startIndex + 2).toIntOrNull(16) ?: return Color.White
    val green =
        string.substring(startIndex + 2, startIndex + 4).toIntOrNull(16) ?: return Color.White
    val blue =
        string.substring(startIndex + 4, startIndex + 6).toIntOrNull(16) ?: return Color.White
    return if (withAlpha) {
        val alpha = string.substring(0, 2).toIntOrNull(16) ?: return Color.White
        Color(red, green, blue, alpha)
    } else {
        Color(red, green, blue)
    }
}

fun Color.toHex(): String {
    return "#$alpha$red$green$blue"
}