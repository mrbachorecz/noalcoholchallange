package com.mrbachorecz.noalcohol.notifications

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

/**
 * Re-schedules the daily notification after reboot or app update,
 * since AlarmManager alarms do not survive process death / reboot.
 */
class BootCompletedReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        val action = intent?.action ?: return
        if (action != Intent.ACTION_BOOT_COMPLETED &&
            action != Intent.ACTION_MY_PACKAGE_REPLACED
        ) {
            return
        }
        NotificationScheduler.rescheduleIfEnabled(context.applicationContext)
    }
}
