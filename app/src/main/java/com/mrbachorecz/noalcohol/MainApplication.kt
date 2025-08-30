package com.mrbachorecz.noalcohol

import android.app.Application
import com.mrbachorecz.noalcohol.storage.readThemeSetting // Your storage function
import com.mrbachorecz.noalcohol.theme.ThemeManager

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        // Initialize ThemeManager with the saved theme
        val savedTheme = readThemeSetting(applicationContext) // Ensure this returns ThemeSetting
        ThemeManager.updateTheme(savedTheme)
    }
}