package com.mrbachorecz.noalcohol.medals

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.mrbachorecz.noalcohol.theme.UITheme
import com.mrbachorecz.noalcohol.maincard.DaysCalculator.calculateDaysPassed
import com.mrbachorecz.noalcohol.storage.readBestMedalEver
import com.mrbachorecz.noalcohol.storage.readLastDrinkingDate
import com.mrbachorecz.noalcohol.storage.writeBestMedalEver

class BestMedalsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val storedDate = readLastDrinkingDate(this)
        val numberOfDays = calculateDaysPassed(storedDate)
        val bestMedal = readBestMedalEver(this)
        setContent {
            UITheme {
                BestMedalsScreen(
                    numberOfDays = numberOfDays,
                    bestMedal = bestMedal,
                    confirm = {
                        writeBestMedalEver(this, numberOfDays)
                    },
                    onClose= {finish()}
                )
            }
        }
    }
}