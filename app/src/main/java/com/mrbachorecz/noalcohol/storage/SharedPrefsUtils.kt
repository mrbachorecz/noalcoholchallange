package com.mrbachorecz.noalcohol.storage

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

private const val PREFS_NAME = "app_prefs"
private const val STORED_DATE_KEY = "storedDate"
private const val NOTIFICATION_ALLOWED_KEY = "notificationAllowed"
private const val NOTIFICATION_HOURS_KEY = "notificationHours"
private const val NOTIFICATION_MINUTES_KEY = "notificationMinutes"

fun readLastDrinkingDate(context: Context): String {
    val prefs: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    return prefs.getString(STORED_DATE_KEY, "") ?: ""
}

fun writeLastDrinkingDate(context: Context, value: String) {
    val prefs: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    prefs.edit { putString(STORED_DATE_KEY, value) }
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