package com.mrbachorecz.noalcohol

import android.content.Intent
import androidx.activity.ComponentActivity
import android.os.Bundle

const val STORED_DATE_KEY = "storedDate"

class InitActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val prefs = getSharedPreferences("app_prefs", MODE_PRIVATE)
        val storedDate = prefs.getString(STORED_DATE_KEY, null)

        if (storedDate == null) {
            startActivity(Intent(this, DatePickerActivity::class.java))
        } else {
            startActivity(Intent(this, MainCardActivity::class.java))
        }
        finish()
    }
}