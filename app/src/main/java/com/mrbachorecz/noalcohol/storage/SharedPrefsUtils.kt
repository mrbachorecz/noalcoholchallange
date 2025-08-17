package com.mrbachorecz.noalcohol.storage

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

private const val PREFS_NAME = "app_prefs"
private const val STORED_DATE_KEY = "storedDate"
private const val MORNING_NOTIFICATION_ALLOWED_KEY = "morningNotificationAllowed"

fun readLastDrinkingDate(context: Context): String {
    val prefs: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    return prefs.getString(STORED_DATE_KEY, "") ?: ""
}

fun writeLastDrinkingDate(context: Context, value: String) {
    val prefs: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    prefs.edit { putString(STORED_DATE_KEY, value) }
}

fun readMorningNotificationAllowed(context: Context): Boolean {
    val prefs: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    return prefs.getBoolean(MORNING_NOTIFICATION_ALLOWED_KEY, false)
}

fun writeMorningNotificationAllowed(context: Context, value: Boolean) {
    val prefs: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    prefs.edit { putBoolean(MORNING_NOTIFICATION_ALLOWED_KEY, value) }
}