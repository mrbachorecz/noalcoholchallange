package com.mrbachorecz.noalcohol.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.Card
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Picker
import androidx.wear.compose.material.Text
import androidx.wear.compose.material.rememberPickerState
import java.time.LocalDate
import java.time.YearMonth

@Composable
fun WearDatePickerDialog(
    initialDate: LocalDate = LocalDate.now(),
    onConfirm: (LocalDate) -> Unit,
    onDismiss: () -> Unit
) {
    val today = LocalDate.now()
    val start = initialDate.coerceAtMost(today)
    val minYear = today.year - 40

    val dayState = rememberPickerState(
        initialNumberOfOptions = 31,
        initiallySelectedOption = start.dayOfMonth - 1
    )
    val monthState = rememberPickerState(
        initialNumberOfOptions = 12,
        initiallySelectedOption = start.monthValue - 1
    )
    val yearState = rememberPickerState(
        initialNumberOfOptions = today.year - minYear + 1,
        initiallySelectedOption = start.year - minYear
    )

    Dialog(onDismissRequest = onDismiss) {
        Card(
            onClick = {},
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(24.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Text(
                    text = "Last drink",
                    style = MaterialTheme.typography.title3,
                    textAlign = TextAlign.Center
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(84.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Picker(
                        state = dayState,
                        modifier = Modifier.weight(1f),
                        contentDescription = "Day"
                    ) { index ->
                        Text(
                            text = (index + 1).toString().padStart(2, '0'),
                            style = MaterialTheme.typography.body1
                        )
                    }
                    Picker(
                        state = monthState,
                        modifier = Modifier.weight(1f),
                        contentDescription = "Month"
                    ) { index ->
                        Text(
                            text = (index + 1).toString().padStart(2, '0'),
                            style = MaterialTheme.typography.body1
                        )
                    }
                    Picker(
                        state = yearState,
                        modifier = Modifier.weight(1.3f),
                        contentDescription = "Year"
                    ) { index ->
                        Text(
                            text = (minYear + index).toString(),
                            style = MaterialTheme.typography.body1
                        )
                    }
                }
                Button(
                    onClick = {
                        val year = minYear + yearState.selectedOption
                        val month = (monthState.selectedOption + 1).coerceIn(1, 12)
                        val maxDay = YearMonth.of(year, month).lengthOfMonth()
                        val day = (dayState.selectedOption + 1).coerceIn(1, maxDay)
                        val picked = LocalDate.of(year, month, day).coerceAtMost(today)
                        onConfirm(picked)
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Confirm")
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
}
