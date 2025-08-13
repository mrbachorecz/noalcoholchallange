package com.mrbachorecz.noalcohol

import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FancyDateInput(
    selectedDate: String,
    onDateSelected: (String?) -> Unit
) {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    val initialDate = try {
        LocalDate.parse(selectedDate, formatter)
    } catch (_: Exception) {
        LocalDate.now()
    }

    val initialMillis = initialDate.toEpochDay() * 24 * 60 * 60 * 1000
    val datePickerState = rememberDatePickerState(initialSelectedDateMillis = initialMillis)

    DatePickerDialog(
        onDismissRequest = {
            if (selectedDate.isEmpty()) onDateSelected(null)
            else onDateSelected(selectedDate)
        },
        confirmButton = {
            TextButton(onClick = {
                val millis = datePickerState.selectedDateMillis
                if (millis != null) {
                    val pickedDate = LocalDate.ofEpochDay(millis / (24 * 60 * 60 * 1000))
                    onDateSelected(pickedDate.format(formatter))
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
        DatePicker(state = datePickerState)
    }
}