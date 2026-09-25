package com.mrbachorecz.noalcohol.wear.storage

import android.content.Context
import androidx.core.content.edit

private const val PREFS_NAME = "wear_prefs"
private const val STORED_DATE_KEY = "storedDate"

fun readLastDrinkingDate(context: Context): String {
    val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    return prefs.getString(STORED_DATE_KEY, "") ?: ""
}

fun writeLastDrinkingDate(context: Context, value: String) {
    val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    prefs.edit(commit = true) { putString(STORED_DATE_KEY, value) }
}
