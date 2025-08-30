package com.mrbachorecz.noalcohol.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mrbachorecz.noalcohol.SubMenuTitleWithClose
import com.mrbachorecz.noalcohol.submitbutton.BottomSubmitButton
import com.mrbachorecz.noalcohol.theme.ThemeManager
import com.mrbachorecz.noalcohol.theme.ThemeSetting

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    storedAllowedNotification: Boolean,
    storedNotificationHours: Int,
    storedNotificationMinutes: Int,
    storedThemeSetting: ThemeSetting,
    onClose: () -> Unit,
    onSave: (Boolean, Int, Int, ThemeSetting) -> Unit
) {
    val allowNotification = remember { mutableStateOf(storedAllowedNotification) }
    val selectedHour = remember { mutableIntStateOf(storedNotificationHours) }
    val selectedMinute = remember { mutableIntStateOf(storedNotificationMinutes) }
    val showTimePicker = remember { mutableStateOf(false) }
    val currentThemeSetting = remember { mutableStateOf(storedThemeSetting) }

    val timePickerState = rememberTimePickerState(
        initialHour = selectedHour.intValue,
        initialMinute = selectedMinute.intValue,
        is24Hour = true
    )

    LaunchedEffect(currentThemeSetting.value) {
        ThemeManager.updateTheme(currentThemeSetting.value)
    }

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
                onSave(
                    allowNotification.value,
                    selectedHour.intValue,
                    selectedMinute.intValue,
                    currentThemeSetting.value
                )
                onClose()
            })
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 8.dp)
        ) {
            NotificationSettingsSection(
                allowNotification = allowNotification.value,
                onAllowNotificationChange = { allowNotification.value = it },
                notificationTimePickerState = timePickerState,
                onTimePickerClick = {
                    if (allowNotification.value) {
                        showTimePicker.value = true
                    }
                },
                isTimeSettingEnabled = allowNotification.value
            )

            ThemeSettingsSection(
                currentThemeSetting = currentThemeSetting.value,
                onThemeSettingChange = { newSetting ->
                    currentThemeSetting.value = newSetting
                }
            )

            if (showTimePicker.value && allowNotification.value) { // Ensure dialog only shows if allowed
                NotificationTimePickerDialog(
                    timePickerState = timePickerState,
                    onDismiss = { showTimePicker.value = false },
                    onConfirm = {
                        selectedHour.intValue = timePickerState.hour
                        selectedMinute.intValue = timePickerState.minute
                        showTimePicker.value = false
                    }
                )
            }
        }
    }
}
