package com.mrbachorecz.noalcohol.notifications

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import androidx.core.app.NotificationCompat
import com.mrbachorecz.noalcohol.DaysCalculator.calculateDaysPassed
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
        val daysPassed = calculateDaysPassed(storedDate)

        val largeIcon = BitmapFactory.decodeResource(context.resources, R.drawable.noalcoholchallange_large_notification_icon)

        val notification = NotificationCompat.Builder(context, channelId)
            .setContentTitle("Congratulation 🎉")
            .setContentText(daysPassed)
            .setSmallIcon(R.drawable.small_notification_icon)
            .setLargeIcon(largeIcon)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(1, notification)
    }
}