package com.mrbachorecz.noalcohol

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.colorResource

@Composable
fun UITheme(content: @Composable () -> Unit) {
    val darkTheme = isSystemInDarkTheme()
    val background = colorResource(id = R.color.colorBackground)
    val colors = if (darkTheme) {
        darkColorScheme(background = background)
    } else {
        lightColorScheme(background = background)
    }
    MaterialTheme(
        colorScheme = colors,
        content = content
    )
}