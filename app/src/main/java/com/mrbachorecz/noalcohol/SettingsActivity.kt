package com.mrbachorecz.noalcohol

import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import com.mrbachorecz.noalcohol.notifications.NotificationScheduler
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
                            if (allowNotification) {
                                checkAndRequestNotificationPermission()
                                NotificationScheduler.scheduleDailyNotification(context, selectedHour, selectedMinute)
                            } else {
                                NotificationScheduler.cancelDailyNotification(context)
                            }
                        }
                    )
                }
            }
        }
    }

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            // Permission granted, proceed with scheduling notifications
        } else {
            // Permission denied, show a message or disable notification features
        }
    }

    fun checkAndRequestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    this, android.Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                requestPermissionLauncher.launch(android.Manifest.permission.POST_NOTIFICATIONS)
            } else {
                // Permission already granted
            }
        }
    }
}