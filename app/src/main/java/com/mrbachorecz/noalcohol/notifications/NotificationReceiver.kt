package com.mrbachorecz.noalcohol.notifications

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.mrbachorecz.noalcohol.InitActivity
import com.mrbachorecz.noalcohol.maincard.DaysCalculator.calculateDaysPassedMessage
import com.mrbachorecz.noalcohol.R
import com.mrbachorecz.noalcohol.storage.readLastDrinkingDate

class NotificationReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        val channelId = "daily_notification_channel"
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager


        val channel = NotificationChannel(
            channelId,
            "Daily Notification",
            NotificationManager.IMPORTANCE_DEFAULT
        )
        notificationManager.createNotificationChannel(channel)

        val storedDate = readLastDrinkingDate(context)
        val daysPassed = calculateDaysPassedMessage(storedDate)

        val launchIntent = Intent(context, InitActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent = PendingIntent.getActivity(
            context, 0, launchIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(context, channelId)
            .setContentTitle("Congratulation 🎉")
            .setContentText(daysPassed)
            .setSmallIcon(R.drawable.small_notification_icon)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .build()

        notificationManager.notify(1, notification)
    }
}