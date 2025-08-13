package com.mrbachorecz.noalcohol

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity

const val STORED_DATE_KEY = "storedDate"

class InitActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val prefs = getSharedPreferences("app_prefs", MODE_PRIVATE)
        val storedDate = prefs.getString(STORED_DATE_KEY, null)

        if (storedDate == null) {
            setContentView(R.layout.activity_init)
            findViewById<Button>(R.id.openDatePickerButton).setOnClickListener {
                startActivity(Intent(this, DatePickerActivity::class.java))
                finish()
            }
        } else {
            startActivity(Intent(this, MainCardActivity::class.java))
            finish()
        }
    }
}