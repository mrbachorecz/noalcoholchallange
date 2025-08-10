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

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val prefs = getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
            var storedDate by remember { mutableStateOf(prefs.getString("start_date", null)) }
            var inputDate by remember { mutableStateOf("") }


            if (storedDate != null) {
                MainCard(
                    storedDate = storedDate!!,
                )

                ResetButton(
                    onReset = {
                        inputDate = storedDate!!
                        storedDate = null
                    }
                )
            } else {
                FancyDateInput(
                    selectedDate = inputDate,
                    onDateSelected = { date ->
                        prefs.edit { putString("start_date", date) }
                        storedDate = date
                    }
                )
            }
        }
    }
}