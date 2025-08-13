package com.mrbachorecz.noalcohol

import android.app.DatePickerDialog
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import java.util.Calendar

@Composable
fun FancyDateInput(
    selectedDate: String,
    onDateSelected: (String?) -> Unit
) {
    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    if (selectedDate.isNotEmpty()) {
        try {
            val parts = selectedDate.split("-")
            if (parts.size == 3) {
                val year = parts[0].toInt()
                val month = parts[1].toInt() - 1
                val day = parts[2].toInt()
                calendar.set(year, month, day)
            }
        } catch (_: Exception) {
        }
    }

    val themeId = android.R.style.ThemeOverlay_Material_Dialog
    val datePickerDialog = DatePickerDialog(
        context,
        themeId,
        { _, year, month, dayOfMonth ->
            val date = "%04d-%02d-%02d".format(year, month + 1, dayOfMonth)
            onDateSelected(date)
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )
    datePickerDialog.datePicker.maxDate = System.currentTimeMillis()

    datePickerDialog.setOnCancelListener {
        if (selectedDate.isEmpty()) {
            onDateSelected(null)
        } else {
            onDateSelected(selectedDate)
        }
    }

    LaunchedEffect(Unit) {
        datePickerDialog.show()
    }
}