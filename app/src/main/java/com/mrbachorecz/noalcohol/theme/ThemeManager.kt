package com.mrbachorecz.noalcohol.theme

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

// Option 1: Singleton Object
object ThemeManager {
    // Assuming ThemeSetting is an enum or a class representing your theme options
    private val _themeSetting = MutableStateFlow(ThemeSetting.SYSTEM) // Default theme
    val themeSetting: StateFlow<ThemeSetting> = _themeSetting.asStateFlow()

    fun updateTheme(newTheme: ThemeSetting) {
        _themeSetting.value = newTheme
    }
}

// Enum for your theme options (example)
enum class ThemeSetting {
    LIGHT, DARK, SYSTEM
}
