package com.example.gemipost.utils

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
fun String.adaptive(maxLength: Int) = if (this.length > maxLength) {
    this.substring(0, maxLength-3) + "..."
} else {
    this
}
fun Long.toReadableSize(): String {
    if (this <= 0) return "0"
    val units = arrayOf("B", "KB", "MB", "GB", "TB")
    val digitGroups = (Math.log10(this.toDouble()) / Math.log10(1024.0)).toInt()
    return String.format("%.1f %s", this / Math.pow(1024.0, digitGroups.toDouble()), units[digitGroups])
}