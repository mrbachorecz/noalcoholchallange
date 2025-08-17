package com.mrbachorecz.noalcohol.notifications

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.mrbachorecz.noalcohol.DaysCalculator.calculateDaysPassed
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
        val daysPassed = calculateDaysPassed(storedDate)
        val notificationText = "No alcohol: $daysPassed days"


        val notification = NotificationCompat.Builder(context, channelId)
            .setContentTitle("Congratulation 🎉")
            .setContentText(notificationText)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(1, notification)
    }
}