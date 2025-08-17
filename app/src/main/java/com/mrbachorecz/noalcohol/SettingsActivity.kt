package com.mrbachorecz.noalcohol

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.ui.platform.LocalContext
import com.mrbachorecz.noalcohol.storage.writeNotificationAllowed
import com.mrbachorecz.noalcohol.storage.writeNotificationHours
import com.mrbachorecz.noalcohol.storage.writeNotificationMinutes
import com.mrbachorecz.noalcohol.storage.readNotificationAllowed
import com.mrbachorecz.noalcohol.storage.readNotificationHours
import com.mrbachorecz.noalcohol.storage.readNotificationMinutes

class SettingsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            UITheme {
                val context = LocalContext.current
                Column {
                    SettingsScreen(
                        storedAllowedNotification = readNotificationAllowed(context),
                        storedNotificationHours = readNotificationHours(context),
                        storedNotificationMinutes = readNotificationMinutes(context),
                        onClose = { (context as? ComponentActivity)?.finish() },
                        onSave = { allowNotification, selectedHour, selectedMinute ->
                            writeNotificationAllowed(context, allowNotification)
                            writeNotificationHours(context, selectedHour)
                            writeNotificationMinutes(context, selectedMinute)
                        }
                    )
                }
            }
        }
    }
}