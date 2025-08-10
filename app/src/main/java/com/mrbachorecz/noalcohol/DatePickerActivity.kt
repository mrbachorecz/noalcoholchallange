package com.mrbachorecz.noalcohol

import android.content.Intent
import androidx.activity.ComponentActivity
import android.os.Bundle
import androidx.activity.compose.setContent

class DatePickerActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val prefs = getSharedPreferences("app_prefs", MODE_PRIVATE)
        val storedDate = prefs.getString(STORED_DATE_KEY, "")

        // This activity is used to show the date picker dialog
        // It will be replaced by FancyDateInput in MainCardActivity
        setContent {
            FancyDateInput(
                selectedDate = storedDate ?:"",
                onDateSelected = { date ->
                    prefs.edit().putString(STORED_DATE_KEY, date).apply()
                    startActivity(Intent(this, MainCardActivity::class.java))
                    finish() // Close this activity after selection
                }
            )
        }
    }
}