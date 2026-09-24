package com.mrbachorecz.noalcohol.notifications

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import com.mrbachorecz.noalcohol.storage.readNotificationAllowed
import com.mrbachorecz.noalcohol.storage.readNotificationHours
import com.mrbachorecz.noalcohol.storage.readNotificationMinutes
import java.util.Calendar

object NotificationScheduler {
    private const val REQUEST_CODE = 1001

    fun rescheduleIfEnabled(context: Context) {
        if (!readNotificationAllowed(context)) return
        if (!NotificationPermissionUtils.hasPostNotificationsPermission(context)) return
        scheduleDailyNotification(
            context,
            readNotificationHours(context),
            readNotificationMinutes(context)
        )
    }

    fun scheduleDailyNotification(context: Context, hour: Int, minute: Int) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val pendingIntent = pendingIntent(context)

        val calendar = Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, minute)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
            if (before(Calendar.getInstance())) {
                add(Calendar.DATE, 1)
            }
        }

        // One-shot alarm; NotificationReceiver reschedules the next day.
        // Prefer exact when allowed; otherwise fall back to inexact while-idle.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S && !alarmManager.canScheduleExactAlarms()) {
            alarmManager.setAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                pendingIntent
            )
        } else {
            alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                pendingIntent
            )
        }
    }

    fun cancelDailyNotification(context: Context) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.cancel(pendingIntent(context))
    }

    private fun pendingIntent(context: Context): PendingIntent {
        val intent = Intent(context, NotificationReceiver::class.java)
        return PendingIntent.getBroadcast(
            context,
            REQUEST_CODE,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
    }
}
