package com.mrbachorecz.noalcohol

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.core.content.edit
import java.time.LocalDate

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val prefs = getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
            var storedDate by remember { mutableStateOf(prefs.getString("start_date", null)) }
            var inputDate by remember { mutableStateOf("") }


            MainCard(
                prefs = prefs,
                storedDate = storedDate,
                inputDate = inputDate,
                onInputDateChange = { inputDate = it },
                onSaveDate = { date ->
                    try {
                        // Validate date format
                        LocalDate.parse(date, java.time.format.DateTimeFormatter.ISO_LOCAL_DATE)
                        prefs.edit { putString("start_date", date) }
                        storedDate = date
                    } catch (_: Exception) { /* handle invalid date */ }
                }
            )

            ResetButton(
                onReset = {
                    prefs.edit { remove("start_date") }
                    storedDate = null
                    inputDate = ""
                }
            )
        }
    }
}