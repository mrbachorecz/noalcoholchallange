package com.mrbachorecz.noalcohol.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.mrbachorecz.noalcohol.storage.readThemeSetting

val SmurfBlueLightColorScheme = lightColorScheme(
    primary = Color(0xFF335889),
    onPrimary = Color.White,
    primaryContainer = Color(0xFFFBFBFB),
    onPrimaryContainer = Color.White,
    secondary = Color(0xFF4FC3FF),
    onSecondary = Color.Black,
    secondaryContainer = Color(0xFFB3E5FF),
    onSecondaryContainer = Color.Black
)

val SmurfBlueDarkColorScheme = darkColorScheme(
    primary = Color(0xFF4A6FA0),
    onPrimary = Color.White,
    primaryContainer = Color(0xFF292929),
    onPrimaryContainer = Color.White,
    secondary = Color(0xFF1976D2),
    onSecondary = Color.White,
    secondaryContainer = Color(0xFF1565C0),
    onSecondaryContainer = Color.White,
    inversePrimary = Color(0xFFB3E5FF)
)

@Composable
fun UITheme(
    content: @Composable () -> Unit
) {
    val themeSetting = readThemeSetting(LocalContext.current)
    val colors = if (themeSetting == ThemeSetting.SYSTEM && isSystemInDarkTheme() || themeSetting == ThemeSetting.DARK) {
        SmurfBlueDarkColorScheme
    } else {
        SmurfBlueLightColorScheme
    }
    MaterialTheme(
        colorScheme = colors,
        content = content
    )
}