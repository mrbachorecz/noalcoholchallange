package com.mrbachorecz.noalcohol.theme

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

object ThemeManager {

    private val _themeSetting = MutableStateFlow(ThemeSetting.SYSTEM)
    val themeSetting: StateFlow<ThemeSetting> = _themeSetting.asStateFlow()

    fun updateTheme(newTheme: ThemeSetting) {
        _themeSetting.value = newTheme
    }

    fun isLightTheme(): Boolean {
        return _themeSetting.value == ThemeSetting.LIGHT
    }

    fun isSystemTheme(): Boolean {
        return _themeSetting.value == ThemeSetting.SYSTEM
    }
}

enum class ThemeSetting {
    LIGHT, DARK, SYSTEM
}
