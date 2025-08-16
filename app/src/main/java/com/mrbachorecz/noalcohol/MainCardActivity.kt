package com.mrbachorecz.noalcohol

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

class MainCardActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            UITheme {
                val prefs = getSharedPreferences("app_prefs", MODE_PRIVATE)
                val storedDate = prefs.getString(STORED_DATE_KEY, "")

                if (storedDate != null && storedDate.isNotEmpty()) {
                    MainCardScreen(
                        storedDate = storedDate,
                        onReset = {
                            startActivity(Intent(this, DatePickerActivity::class.java))
                            finish()
                        },
                        onSettingsClick = { /* TODO: Navigate to settings */ }
                    )
                }
            }
        }
    }
}