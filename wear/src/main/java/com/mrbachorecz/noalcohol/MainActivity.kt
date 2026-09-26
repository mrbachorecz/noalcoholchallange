package com.mrbachorecz.noalcohol

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.wear.compose.material.MaterialTheme
import com.mrbachorecz.noalcohol.shared.DaysCalculator
import com.mrbachorecz.noalcohol.storage.readLastDrinkingDate
import com.mrbachorecz.noalcohol.sync.WearSyncRequester
import com.mrbachorecz.noalcohol.ui.DaysCircle
import com.mrbachorecz.noalcohol.ui.WearInitDateScreen
import com.mrbachorecz.noalcohol.ui.WearResetStreakScreen

class MainActivity : ComponentActivity() {

    private var storedDateState by mutableStateOf("")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        storedDateState = readLastDrinkingDate(this)
        WearSyncRequester.requestRefresh(this)
        setContent {
            MaterialTheme {
                WearHomeScreen(
                    storedDate = storedDateState,
                    onDateChanged = { storedDateState = it }
                )
            }
        }
    }

    override fun onResume() {
        super.onResume()
        storedDateState = readLastDrinkingDate(this)
        WearSyncRequester.requestRefresh(this)
    }
}

@Composable
private fun WearHomeScreen(
    storedDate: String,
    onDateChanged: (String) -> Unit
) {
    val hasDate = DaysCalculator.parseStoredDate(storedDate) != null
    val days = DaysCalculator.calculateDaysPassed(storedDate)
    var showResetDialog by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        if (!hasDate) {
            WearInitDateScreen(onDateSet = onDateChanged)
        } else if (showResetDialog) {
            WearResetStreakScreen(
                onDateSet = { iso ->
                    onDateChanged(iso)
                    showResetDialog = false
                },
                onDismiss = { showResetDialog = false }
            )
        } else {
            DaysCircle(
                days = days,
                modifier = Modifier
                    .fillMaxSize()
                    .clickable { showResetDialog = true }
            )
        }
    }
}
