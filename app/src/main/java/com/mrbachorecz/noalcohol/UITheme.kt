package com.mrbachorecz.noalcohol

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val SmurfBlueLightColorScheme = lightColorScheme(
    primary = Color(0xFF4FC3FF),
    onPrimary = Color.White,
    primaryContainer = Color(0xFFB3E5FF),
    onPrimaryContainer = Color.Black,
    secondary = Color(0xFF64B5F6),
    onSecondary = Color.White,
    secondaryContainer = Color(0xFFBBDEFB),
    onSecondaryContainer = Color.Black
)

val SmurfBlueDarkColorScheme = darkColorScheme(
    primary = Color(0xFF1565C0),
    onPrimary = Color.Black,
    primaryContainer = Color(0xFF1976D2),
    onPrimaryContainer = Color.Black,
    secondary = Color(0xFF1E88E5),
    onSecondary = Color.Black,
    secondaryContainer = Color(0xFF42A5F5),
    onSecondaryContainer = Color.Black,
    inversePrimary = Color(0xFFBBDEFB)
)

@Composable
fun UITheme(content: @Composable () -> Unit) {
    val colors = if (isSystemInDarkTheme()) {
        SmurfBlueDarkColorScheme
    } else {
        SmurfBlueLightColorScheme
    }
    MaterialTheme(
        colorScheme = colors,
        content = content
    )
}