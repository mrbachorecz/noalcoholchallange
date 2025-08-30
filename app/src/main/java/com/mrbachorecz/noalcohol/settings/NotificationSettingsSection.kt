package com.mrbachorecz.noalcohol.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import java.util.Locale


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationSettingsSection(
    allowNotification: Boolean,
    onAllowNotificationChange: (Boolean) -> Unit,
    notificationTimePickerState: TimePickerState,
    onTimePickerClick: () -> Unit,
    isTimeSettingEnabled: Boolean
) {
    val dividerColor = Color.Gray
    Column {
        HorizontalDivider(
            color = dividerColor,
            thickness = 3.dp,
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Allow notifications", modifier = Modifier.weight(1f))
            Switch(
                checked = allowNotification,
                onCheckedChange = onAllowNotificationChange
            )
        }
        HorizontalDivider(
            color = dividerColor,
            thickness = 1.dp,
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(enabled = isTimeSettingEnabled) { // Only clickable if enabled
                    if (isTimeSettingEnabled) {
                        onTimePickerClick()
                    }
                }
                .padding(vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Notification time",
                modifier = Modifier.weight(1f),
                color = if (isTimeSettingEnabled) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f)
            )
            Text(
                text = String.format(Locale.getDefault(), "%02d:%02d", notificationTimePickerState.hour, notificationTimePickerState.minute),
                color = if (isTimeSettingEnabled) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f)
            )
        }
    }
}
