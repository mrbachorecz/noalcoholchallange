package com.mrbachorecz.noalcohol.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.mrbachorecz.noalcohol.SubMenuTitleWithClose
import com.mrbachorecz.noalcohol.submitbutton.BottomSubmitButton
import java.util.Locale


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    storedAllowedNotification: Boolean,
    storedNotificationHours: Int,
    storedNotificationMinutes: Int,
    onClose: () -> Unit,
    onSave: (Boolean, Int, Int) -> Unit
) {
    val allowNotification = remember { mutableStateOf(storedAllowedNotification) }
    val selectedHour = remember { mutableIntStateOf(storedNotificationHours) }
    val selectedMinute = remember { mutableIntStateOf(storedNotificationMinutes) }
    val showTimePicker = remember { mutableStateOf(false) }

    val dividerColor = Color.Gray
    val timePickerState = rememberTimePickerState(
        initialHour = selectedHour.intValue,
        initialMinute = selectedMinute.intValue,
        is24Hour = true
    )

    Scaffold(
        topBar = {
            SubMenuTitleWithClose(
                title = "Settings",
                imageVector = Icons.Filled.Settings,
                onClose = onClose
            )
        },
        bottomBar = {
            BottomSubmitButton(text = "Save", onSubmit = {
                onSave(allowNotification.value, selectedHour.intValue, selectedMinute.intValue)
                onClose()
            })
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            HorizontalDivider(
                color = dividerColor,
                thickness = 3.dp,
                modifier = Modifier.padding(horizontal = 8.dp)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Allow morning notification",
                    modifier = Modifier.weight(1f),
                    color = MaterialTheme.colorScheme.onBackground
                )
                Switch(
                    checked = allowNotification.value,
                    onCheckedChange = { allowNotification.value = it }
                )
            }
            HorizontalDivider(
                color = dividerColor,
                thickness = 1.dp,
                modifier = Modifier.padding(horizontal = 8.dp)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp)
                    .clickable { showTimePicker.value = true },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Notification time",
                    modifier = Modifier.weight(1f),
                    color = MaterialTheme.colorScheme.onBackground
                )
                Text(
                    text = String.format(
                        Locale.getDefault(),
                        "%02d:%02d",
                        timePickerState.hour,
                        timePickerState.minute
                    ),
                    color = MaterialTheme.colorScheme.primary
                )
            }
            HorizontalDivider(
                color = dividerColor,
                thickness = 3.dp,
                modifier = Modifier.padding(horizontal = 8.dp)
            )
            if (showTimePicker.value) {
                AlertDialog(
                    onDismissRequest = { showTimePicker.value = false },
                    confirmButton = {
                        TextButton(onClick = {
                            selectedHour.intValue = timePickerState.hour
                            selectedMinute.intValue = timePickerState.minute
                            showTimePicker.value = false
                        }) {
                            Text("OK")
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = { showTimePicker.value = false }) {
                            Text("Cancel")
                        }
                    },
                    text = {
                        TimePicker(state = timePickerState)
                    }
                )
            }
        }
    }
}