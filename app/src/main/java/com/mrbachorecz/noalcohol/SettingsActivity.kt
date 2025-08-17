package com.mrbachorecz.noalcohol

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.ui.platform.LocalContext
import com.mrbachorecz.noalcohol.notifications.NotificationPermissionUtils
import com.mrbachorecz.noalcohol.notifications.NotificationPermissionUtils.checkAndRequestNotificationPermission
import com.mrbachorecz.noalcohol.notifications.NotificationScheduler
import com.mrbachorecz.noalcohol.storage.readNotificationAllowed
import com.mrbachorecz.noalcohol.storage.readNotificationHours
import com.mrbachorecz.noalcohol.storage.readNotificationMinutes
import com.mrbachorecz.noalcohol.storage.writeNotificationAllowed
import com.mrbachorecz.noalcohol.storage.writeNotificationHours
import com.mrbachorecz.noalcohol.storage.writeNotificationMinutes

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
                            if (allowNotification) {
                                checkAndRequestNotificationPermission(
                                    this@SettingsActivity,
                                    requestPermissionLauncher
                                )
                                NotificationScheduler.scheduleDailyNotification(
                                    context,
                                    selectedHour,
                                    selectedMinute
                                )
                            } else {
                                NotificationScheduler.cancelDailyNotification(context)
                            }
                        }
                    )
                }
            }
        }
    }

    private val requestPermissionLauncher by lazy {
        NotificationPermissionUtils.createRequestPermissionLauncher(this) { isGranted ->
            // Handle permission result
        }
    }
}