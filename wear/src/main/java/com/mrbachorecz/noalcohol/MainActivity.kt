package com.mrbachorecz.noalcohol

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.Card
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Text
import com.mrbachorecz.noalcohol.shared.DaysCalculator
import com.mrbachorecz.noalcohol.storage.readLastDrinkingDate
import com.mrbachorecz.noalcohol.sync.OpenPhoneApp
import com.mrbachorecz.noalcohol.sync.WearDateReset
import com.mrbachorecz.noalcohol.sync.WearSyncRequester
import com.mrbachorecz.noalcohol.ui.DaysCircle

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
    val context = LocalContext.current
    val hasDate = DaysCalculator.parseStoredDate(storedDate) != null
    val days = DaysCalculator.calculateDaysPassed(storedDate)
    var showResetDialog by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        if (!hasDate) {
            Text(
                text = "Tap to open the phone app and sync your streak.",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.body2,
                textDecoration = TextDecoration.Underline,
                color = MaterialTheme.colors.primary,
                modifier = Modifier
                    .padding(16.dp)
                    .clickable(role = Role.Button) {
                        OpenPhoneApp.launch(context)
                    }
            )
        } else {
            DaysCircle(
                days = days,
                modifier = Modifier
                    .fillMaxSize()
                    .clickable { showResetDialog = true }
            )
        }

        if (showResetDialog) {
            Dialog(onDismissRequest = { showResetDialog = false }) {
                Card(
                    onClick = {},
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(24.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = "Reset for today?",
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.title3
                        )
                        Text(
                            text = "Sets your streak start to today.",
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.body2
                        )
                        Button(
                            onClick = {
                                val today = WearDateReset.resetToToday(context)
                                onDateChanged(today)
                                showResetDialog = false
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Yes")
                        }
                        Button(
                            onClick = {
                                showResetDialog = false
                                OpenPhoneApp.launch(context)
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("No, open on phone", textAlign = TextAlign.Center)
                        }
                    }
                }
            }
        }
    }
}
