package com.mrbachorecz.noalcohol.medals

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.mrbachorecz.noalcohol.theme.UITheme
import com.mrbachorecz.noalcohol.maincard.DaysCalculator.calculateDaysPassed
import com.mrbachorecz.noalcohol.storage.readLastDrinkingDate

class MedalsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val storedDate = readLastDrinkingDate(this)
        val numberOfDays = calculateDaysPassed(storedDate)
        setContent {
            UITheme {
                MedalsScreen(
                    numberOfDays = numberOfDays,
                    onClose= {finish()}
                )
            }
        }
    }
}