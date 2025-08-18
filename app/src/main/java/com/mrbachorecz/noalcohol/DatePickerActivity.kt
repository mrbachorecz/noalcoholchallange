package com.mrbachorecz.noalcohol

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.mrbachorecz.noalcohol.maincard.MainCardActivity
import com.mrbachorecz.noalcohol.storage.readLastDrinkingDate
import com.mrbachorecz.noalcohol.storage.writeLastDrinkingDate

class DatePickerActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val storedDate = readLastDrinkingDate(this)

        setContent {
            UITheme {
                FancyDateInput(
                    selectedDate = storedDate,
                    onDateSelected = { date ->
                        if (date != null && date.isNotEmpty()) {
                            writeLastDrinkingDate(this, date)
                            startActivity(Intent(this, MainCardActivity::class.java))
                            finish()
                        } else {
                            startActivity(Intent(this, InitActivity::class.java))
                            finish()
                        }
                    }
                )
            }
        }
    }
}