package com.mrbachorecz.noalcohol

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.platform.LocalContext

class MainCardActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            UITheme {
                val context = LocalContext.current
                val prefs = getSharedPreferences("app_prefs", MODE_PRIVATE)
                val storedDate = prefs.getString(STORED_DATE_KEY, "")

                if (storedDate != null && storedDate.isNotEmpty()) {
                    MainCardScreen(
                        storedDate = storedDate,
                        onReset = {
                            startActivity(Intent(this, DatePickerActivity::class.java))
                            finish()
                        },
                        onSettingsClick = {
                            val intent = Intent(context, SettingsActivity::class.java)
                            context.startActivity(intent)
                        }
                    )
                }
            }
        }
    }
}