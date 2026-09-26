package com.mrbachorecz.noalcohol

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.wear.compose.material.MaterialTheme
import com.mrbachorecz.noalcohol.shared.DaysCalculator
import com.mrbachorecz.noalcohol.storage.readLastDrinkingDate
import com.mrbachorecz.noalcohol.ui.WearInitDateScreen
import com.mrbachorecz.noalcohol.ui.WearResetStreakScreen

/**
 * Opened from the Wear tile. Shows init or reset (Today / Pick date), then finishes —
 * does not open the main circle screen.
 */
class WearTileClickActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val hasDate = DaysCalculator.parseStoredDate(readLastDrinkingDate(this)) != null
        setContent {
            MaterialTheme {
                if (hasDate) {
                    WearResetStreakScreen(
                        onDateSet = { finish() },
                        onDismiss = { finish() }
                    )
                } else {
                    WearInitDateScreen(onDateSet = { finish() })
                }
            }
        }
    }
}
