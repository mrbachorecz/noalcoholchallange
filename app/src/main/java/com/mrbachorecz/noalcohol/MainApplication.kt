package com.mrbachorecz.noalcohol

import android.app.Application
import com.mrbachorecz.noalcohol.notifications.NotificationScheduler
import com.mrbachorecz.noalcohol.storage.readThemeSetting
import com.mrbachorecz.noalcohol.sync.WearSync
import com.mrbachorecz.noalcohol.theme.ThemeManager

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        val savedTheme = readThemeSetting(applicationContext)
        ThemeManager.updateTheme(savedTheme)
        // Alarms may have been lost (Doze, force-stop, etc.); re-arm if the user opted in.
        NotificationScheduler.rescheduleIfEnabled(applicationContext)
        WearSync.syncLastDrinkingDate(applicationContext)
    }
}
