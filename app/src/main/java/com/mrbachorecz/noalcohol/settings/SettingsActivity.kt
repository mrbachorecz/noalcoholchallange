package com.mrbachorecz.noalcohol.settings

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.ui.platform.LocalContext
import com.mrbachorecz.noalcohol.notifications.NotificationPermissionUtils
import com.mrbachorecz.noalcohol.notifications.NotificationScheduler
import com.mrbachorecz.noalcohol.storage.readNotificationAllowed
import com.mrbachorecz.noalcohol.storage.readNotificationHours
import com.mrbachorecz.noalcohol.storage.readNotificationMinutes
import com.mrbachorecz.noalcohol.storage.readThemeSetting
import com.mrbachorecz.noalcohol.storage.writeNotificationAllowed
import com.mrbachorecz.noalcohol.storage.writeNotificationHours
import com.mrbachorecz.noalcohol.storage.writeNotificationMinutes
import com.mrbachorecz.noalcohol.storage.writeThemeSetting
import com.mrbachorecz.noalcohol.theme.UITheme

class SettingsActivity : ComponentActivity() {

    private var pendingHour: Int = 18
    private var pendingMinute: Int = 0
    private var awaitingPermissionForEnable: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val requestPermissionLauncher =
            NotificationPermissionUtils.createRequestPermissionLauncher(this) { isGranted ->
                if (!awaitingPermissionForEnable) return@createRequestPermissionLauncher
                awaitingPermissionForEnable = false
                if (isGranted) {
                    enableNotifications(pendingHour, pendingMinute)
                } else {
                    writeNotificationAllowed(this, false)
                    NotificationScheduler.cancelDailyNotification(this)
                }
                finish()
            }

        setContent {
            UITheme {
                val context = LocalContext.current
                Column {
                    SettingsScreen(
                        storedAllowedNotification = readNotificationAllowed(context),
                        storedNotificationHours = readNotificationHours(context),
                        storedNotificationMinutes = readNotificationMinutes(context),
                        storedThemeSetting = readThemeSetting(context),
                        onClose = {
                            finish()
                        },
                        onSave = { allowNotification, selectedHour, selectedMinute, selectedTheme ->
                            writeNotificationHours(context, selectedHour)
                            writeNotificationMinutes(context, selectedMinute)
                            writeThemeSetting(context, selectedTheme)

                            if (!allowNotification) {
                                writeNotificationAllowed(context, false)
                                NotificationScheduler.cancelDailyNotification(context)
                                finish()
                                return@SettingsScreen
                            }

                            pendingHour = selectedHour
                            pendingMinute = selectedMinute

                            if (NotificationPermissionUtils.hasPostNotificationsPermission(context)) {
                                enableNotifications(selectedHour, selectedMinute)
                                finish()
                            } else {
                                awaitingPermissionForEnable = true
                                NotificationPermissionUtils.checkAndRequestNotificationPermission(
                                    this@SettingsActivity,
                                    requestPermissionLauncher
                                )
                            }
                        }
                    )
                }
            }
        }
    }

    private fun enableNotifications(hour: Int, minute: Int) {
        writeNotificationAllowed(this, true)
        NotificationScheduler.scheduleDailyNotification(this, hour, minute)
    }
}
