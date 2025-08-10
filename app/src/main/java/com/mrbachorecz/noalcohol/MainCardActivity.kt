package com.mrbachorecz.noalcohol

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

class MainCardActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val prefs = getSharedPreferences("app_prefs", MODE_PRIVATE)
            val storedDate = prefs.getString(STORED_DATE_KEY, "")

            if (storedDate != null) {
                MainCard(
                    storedDate = storedDate,
                )

                ResetButton(
                    onReset = {
                        startActivity(Intent(this, DatePickerActivity::class.java))
                        finish()
                    }
                )
            }
        }
    }
}