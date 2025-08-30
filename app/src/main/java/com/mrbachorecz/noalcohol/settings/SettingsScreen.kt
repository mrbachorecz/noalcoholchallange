package com.mrbachorecz.noalcohol.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
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
import com.mrbachorecz.noalcohol.theme.ThemeManager
import com.mrbachorecz.noalcohol.theme.ThemeSetting
import java.util.Locale

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

    val dividerColor = Color.Gray
    val timePickerState = rememberTimePickerState(
        initialHour = selectedHour.intValue,
        initialMinute = selectedMinute.intValue,
        is24Hour = true
    )

    val forceThemeOptionsEnabled = currentThemeSetting.value != ThemeSetting.SYSTEM

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
            HorizontalDivider(
                color = dividerColor,
                thickness = 3.dp,
                modifier = Modifier.padding(horizontal = 8.dp)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Allow notification",
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
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 12.dp)
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
            )
            Text(
                text = "Theme",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(start = 8.dp, top = 16.dp, bottom = 8.dp),
                color = MaterialTheme.colorScheme.onBackground
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                val isSystemDark = isSystemInDarkTheme()
                val currentSystemTheme = if (isSystemDark) "Dark" else "Light"
                Text(
                    text = "Use system settings ($currentSystemTheme)",
                    modifier = Modifier.weight(1f),
                    color = MaterialTheme.colorScheme.onBackground
                )
                Switch(
                    checked = currentThemeSetting.value == ThemeSetting.SYSTEM,
                    onCheckedChange = { isChecked ->
                        if (isChecked) {
                            currentThemeSetting.value = ThemeSetting.SYSTEM
                        } else {
                            currentThemeSetting.value = if (isSystemDark) ThemeSetting.DARK else ThemeSetting.LIGHT
                        }
                        ThemeManager.updateTheme(currentThemeSetting.value)
                    }
                )
            }
            HorizontalDivider(
                color = dividerColor,
                thickness = 1.dp,
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Force theme",
                    modifier = Modifier.weight(1f),
                    color = if (forceThemeOptionsEnabled) MaterialTheme.colorScheme.onBackground else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f)
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
                    .clickable(enabled = forceThemeOptionsEnabled) {
                        if (forceThemeOptionsEnabled) {
                            currentThemeSetting.value = ThemeSetting.LIGHT
                            ThemeManager.updateTheme(currentThemeSetting.value)
                        }
                    },
                verticalAlignment = Alignment.CenterVertically
            ) {
                ThemeSettingOption(
                    text = "Light mode",
                    selected = currentThemeSetting.value == ThemeSetting.LIGHT,
                    enabled = forceThemeOptionsEnabled, // Pass enabled state
                    onClick = {
                        currentThemeSetting.value = ThemeSetting.LIGHT
                        ThemeManager.updateTheme(currentThemeSetting.value)
                    }
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 12.dp)
                    // Disable click if forceThemeOptions are not enabled
                    .clickable(enabled = forceThemeOptionsEnabled) {
                        if (forceThemeOptionsEnabled) {
                            currentThemeSetting.value = ThemeSetting.DARK
                            ThemeManager.updateTheme(currentThemeSetting.value)
                        }
                    },
                verticalAlignment = Alignment.CenterVertically
            ) {
                ThemeSettingOption(
                    text = "Dark mode",
                    selected = currentThemeSetting.value == ThemeSetting.DARK,
                    enabled = forceThemeOptionsEnabled, // Pass enabled state
                    onClick = {
                        currentThemeSetting.value = ThemeSetting.DARK
                        ThemeManager.updateTheme(currentThemeSetting.value)
                    }
                )
            }
            HorizontalDivider(
                color = dividerColor,
                thickness = 3.dp,
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

@Composable
fun ThemeSettingOption(
    text: String,
    selected: Boolean,
    onClick: () -> Unit,
    enabled: Boolean = true // Add enabled parameter with a default value
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(enabled = enabled, onClick = onClick), // Use enabled parameter
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = selected,
            onClick = onClick,
            enabled = enabled // Use enabled parameter
        )
        Spacer(Modifier.width(8.dp))
        Text(
            text = text,
            color = if (enabled) MaterialTheme.colorScheme.onBackground else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f) // Dim text when disabled
        )
    }
}
