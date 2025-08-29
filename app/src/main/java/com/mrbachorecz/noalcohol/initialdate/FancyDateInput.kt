package com.mrbachorecz.noalcohol.initialdate

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.time.LocalDate
import java.time.format.DateTimeFormatter

val INPUT_DATE_FORMATTER: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
const val MILLIS_PER_DAY: Long = 24 * 60 * 60 * 1000

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FancyDateInput(
    selectedDate: String,
    onDateSelected: (String?) -> Unit
) {
    val initialDate = parseDateOrToday(selectedDate)
    val initialMillis = initialDate.toEpochDay() * MILLIS_PER_DAY
    val todayMillis = LocalDate.now().toEpochDay() * MILLIS_PER_DAY
    val noFutureDates = object : SelectableDates {
        override fun isSelectableDate(utcTimeMillis: Long): Boolean {
            return utcTimeMillis <= todayMillis
        }
    }
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = initialMillis,
        selectableDates = noFutureDates
    )

    DatePickerDialog(
        onDismissRequest = {
            if (selectedDate.isEmpty()) onDateSelected(null)
            else onDateSelected(selectedDate)
        },
        confirmButton = {
            TextButton(onClick = {
                val millis = datePickerState.selectedDateMillis
                if (millis != null) {
                    val pickedDate = LocalDate.ofEpochDay(millis / MILLIS_PER_DAY)
                    onDateSelected(pickedDate.format(INPUT_DATE_FORMATTER))
                } else {
                    onDateSelected(null)
                }
            }) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(onClick = {
                if (selectedDate.isEmpty()) onDateSelected(null)
                else onDateSelected(selectedDate)
            }) {
                Text("Cancel")
            }
        }
    ) {
        DatePicker(state = datePickerState, title = {DatePickerTitle("Last drink date")})
    }
}

@Composable
fun DatePickerTitle(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.headlineSmall,
        modifier = Modifier.padding(PaddingValues(start = 24.dp, end = 12.dp, top = 16.dp))
    )
}

fun parseDateOrToday(dateStr: String): LocalDate =
    try {
        LocalDate.parse(dateStr, INPUT_DATE_FORMATTER)
    } catch (_: Exception) {
        LocalDate.now()
    }
