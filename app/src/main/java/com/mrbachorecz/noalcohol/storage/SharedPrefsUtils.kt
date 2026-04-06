package com.mrbachorecz.noalcohol.storage

import android.content.Context
import android.content.SharedPreferences
import androidx.activity.result.launch
import androidx.core.content.edit
import androidx.glance.appwidget.updateAll
import com.mrbachorecz.noalcohol.theme.ThemeSetting
import com.mrbachorecz.noalcohol.widget.DaysCounterWidget
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

private const val PREFS_NAME = "app_prefs"
private const val STORED_DATE_KEY = "storedDate"
private const val NOTIFICATION_ALLOWED_KEY = "notificationAllowed"
private const val NOTIFICATION_HOURS_KEY = "notificationHours"
private const val NOTIFICATION_MINUTES_KEY = "notificationMinutes"
private const val THEME_SETTING_KEY = "themeSetting"
private const val BEST_MEDAL_EVER_KEY = "bestMedalEver"

private const val BATTERY_OPTIMIZATION_PERMISSION = "batteryOptimizationPermission"

fun readLastDrinkingDate(context: Context): String {
    val prefs: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    return prefs.getString(STORED_DATE_KEY, "") ?: ""
}

fun writeLastDrinkingDate(context: Context, value: String) {
    val prefs: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    prefs.edit(commit = true) { putString(STORED_DATE_KEY, value) }
}

fun readNotificationAllowed(context: Context): Boolean {
    val prefs: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    return prefs.getBoolean(NOTIFICATION_ALLOWED_KEY, false)
}

fun writeNotificationAllowed(context: Context, value: Boolean) {
    val prefs: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    prefs.edit { putBoolean(NOTIFICATION_ALLOWED_KEY, value) }
}

fun readNotificationHours(context: Context): Int {
    val prefs: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    return prefs.getInt(NOTIFICATION_HOURS_KEY, 18)
}

fun writeNotificationHours(context: Context, value: Int) {
    val prefs: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    prefs.edit { putInt(NOTIFICATION_HOURS_KEY, value) }
}

fun readNotificationMinutes(context: Context): Int {
    val prefs: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    return prefs.getInt(NOTIFICATION_MINUTES_KEY, 0)
}

fun writeNotificationMinutes(context: Context, value: Int) {
    val prefs: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    prefs.edit { putInt(NOTIFICATION_MINUTES_KEY, value) }
}

fun readThemeSetting(context: Context): ThemeSetting {
    val prefs: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    val themeName = prefs.getString(THEME_SETTING_KEY, "SYSTEM") ?: "SYSTEM"
    return try {
        ThemeSetting.valueOf(themeName)
    } catch (_: IllegalArgumentException) {
        ThemeSetting.SYSTEM
    }
}

fun writeThemeSetting(context: Context, value: ThemeSetting) {
    val prefs: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    prefs.edit { putString(THEME_SETTING_KEY, value.name) }
}

fun readBestMedalEver(context: Context): Int {
    val prefs: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    return prefs.getInt(BEST_MEDAL_EVER_KEY, 0)
}

fun writeBestMedalEver(context: Context, value: Int) {
    val prefs: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    prefs.edit { putInt(BEST_MEDAL_EVER_KEY, value) }
}

fun readBatteryOptimizationPermission(context: Context): String {
    val prefs: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    return prefs.getString(BATTERY_OPTIMIZATION_PERMISSION, "") ?: ""
}

fun writeBatteryOptimizationPermission(context: Context, value: String) {
    val prefs: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    prefs.edit { putString(BATTERY_OPTIMIZATION_PERMISSION, value) }
}
