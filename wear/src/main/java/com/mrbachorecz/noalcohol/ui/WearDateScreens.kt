package com.mrbachorecz.noalcohol.ui

import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.Card
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Text
import com.mrbachorecz.noalcohol.sync.WearDateReset
import java.time.LocalDate

@Composable
fun WearInitDateScreen(
    onDateSet: (String) -> Unit
) {
    val context = LocalContext.current
    var showDatePicker by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Last drink date",
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.title3
        )
        Text(
            text = "Set when your streak starts",
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.body2,
            modifier = Modifier.padding(vertical = 8.dp)
        )
        Button(
            onClick = {
                val iso = WearDateReset.resetToToday(context)
                onDateSet(iso)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
        ) {
            Text("Today")
        }
        Button(
            onClick = { showDatePicker = true },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Pick date")
        }
    }

    if (showDatePicker) {
        WearDatePickerDialog(
            initialDate = LocalDate.now(),
            onConfirm = { date ->
                val iso = WearDateReset.setLastDrinkDate(context, date)
                showDatePicker = false
                onDateSet(iso)
            },
            onDismiss = { showDatePicker = false }
        )
    }
}

@Composable
fun WearResetStreakScreen(
    onDateSet: (String) -> Unit,
    onDismiss: () -> Unit
) {
    val context = LocalContext.current
    var showDatePicker by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
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
                    text = "Reset streak?",
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.title3
                )
                Button(
                    onClick = {
                        val iso = WearDateReset.resetToToday(context)
                        onDateSet(iso)
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Today")
                }
                Button(
                    onClick = { showDatePicker = true },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Pick date")
                }
                Button(
                    onClick = onDismiss,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Cancel")
                }
            }
        }
    }

    if (showDatePicker) {
        WearDatePickerDialog(
            initialDate = LocalDate.now(),
            onConfirm = { date ->
                val iso = WearDateReset.setLastDrinkDate(context, date)
                showDatePicker = false
                onDateSet(iso)
            },
            onDismiss = { showDatePicker = false }
        )
    }
}
