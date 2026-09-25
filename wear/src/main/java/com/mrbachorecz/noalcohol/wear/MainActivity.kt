package com.mrbachorecz.noalcohol.wear

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Text
import com.mrbachorecz.noalcohol.shared.DaysCalculator
import com.mrbachorecz.noalcohol.wear.storage.readLastDrinkingDate
import com.mrbachorecz.noalcohol.wear.sync.OpenPhoneApp
import com.mrbachorecz.noalcohol.wear.sync.WearSyncRequester
import com.mrbachorecz.noalcohol.wear.ui.DaysCircle

class MainActivity : ComponentActivity() {

    private var storedDateState by mutableStateOf("")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        storedDateState = readLastDrinkingDate(this)
        WearSyncRequester.requestRefresh(this)
        setContent {
            MaterialTheme {
                WearHomeScreen(storedDate = storedDateState)
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
private fun WearHomeScreen(storedDate: String) {
    val context = LocalContext.current
    val hasDate = DaysCalculator.parseStoredDate(storedDate) != null
    val days = DaysCalculator.calculateDaysPassed(storedDate)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp),
        contentAlignment = Alignment.Center
    ) {
        if (!hasDate) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .clickable(role = Role.Button) {
                        OpenPhoneApp.launch(context)
                    }
                    .padding(8.dp)
            ) {
                Text(
                    text = "Tap to open the phone app and sync your streak.",
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.body2,
                    textDecoration = TextDecoration.Underline,
                    color = MaterialTheme.colors.primary
                )
                Text(
                    text = "Then add the Days sober tile from the tiles menu.",
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.caption2,
                    color = MaterialTheme.colors.onBackground.copy(alpha = 0.7f)
                )
            }
        } else {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                DaysCircle(days = days, size = 120.dp)
                Text(
                    text = "Add as a tile from the tiles menu",
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.caption2,
                    color = MaterialTheme.colors.onBackground.copy(alpha = 0.7f)
                )
            }
        }
    }
}
