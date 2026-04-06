package com.mrbachorecz.noalcohol.maincard

import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.glance.appwidget.GlanceAppWidgetManager
import androidx.lifecycle.lifecycleScope
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.mrbachorecz.noalcohol.initialdate.DatePickerActivity
import com.mrbachorecz.noalcohol.medals.BestMedalsActivity
import com.mrbachorecz.noalcohol.medals.MedalsActivity
import com.mrbachorecz.noalcohol.settings.SettingsActivity
import com.mrbachorecz.noalcohol.storage.readBestMedalEver
import com.mrbachorecz.noalcohol.storage.readLastDrinkingDate
import com.mrbachorecz.noalcohol.theme.UITheme
import com.mrbachorecz.noalcohol.widget.DailyWidgetWorker
import com.mrbachorecz.noalcohol.widget.DaysCounterWidget
import com.mrbachorecz.noalcohol.widget.DaysCounterWidgetReceiver
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.concurrent.TimeUnit

class MainCardActivity : ComponentActivity() {

    private var maxMedalState by mutableIntStateOf(0)

    override fun onResume() {
        super.onResume()
        val context = this
        maxMedalState = readBestMedalEver(context)

        // Refresh the widget whenever the app is opened
        lifecycleScope.launch {
            val context = applicationContext
            val manager = GlanceAppWidgetManager(context)
            val ids = manager.getGlanceIds(DaysCounterWidget::class.java)

            ids.forEach { id ->
                // This is the most "forceful" update available in the Glance API
                // It bypasses the standard check by targeting the specific ID
                DaysCounterWidget().update(context, id)
            }

            // TRICK: Send a broadcast to the system to tell it the widget's data changed
            val updateIntent = Intent(context, DaysCounterWidgetReceiver::class.java).apply {
                action = AppWidgetManager.ACTION_APPWIDGET_UPDATE
            }
            val appWidgetIds = AppWidgetManager.getInstance(context)
                .getAppWidgetIds(ComponentName(context, DaysCounterWidgetReceiver::class.java))

            updateIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds)
            context.sendBroadcast(updateIntent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            UITheme {
                val context = LocalContext.current
                val storedDate = readLastDrinkingDate(context)
                maxMedalState = readBestMedalEver(context)
                if (storedDate.isNotEmpty()) {
                    MainCardScreen(
                        storedDate = storedDate,
                        maxMedal = maxMedalState,
                        onReset = {
                            startActivity(Intent(this, DatePickerActivity::class.java))
                            finish()
                        },
                        onMedalsClick = {
                            val intent = Intent(context, MedalsActivity::class.java)
                            context.startActivity(intent)
                        },
                        onSettingsClick = {
                            val intent = Intent(context, SettingsActivity::class.java)
                            context.startActivity(intent)
                        },
                        onBestMedalsClick = {
                            val intent = Intent(context, BestMedalsActivity::class.java)
                            context.startActivity(intent)
                        }
                    )
                }
            }
        }

        scheduleDailyWidgetUpdate()
    }

    private fun scheduleDailyWidgetUpdate() {
        // Create the 4-hour request
        val periodicRequest = PeriodicWorkRequestBuilder<DailyWidgetWorker>(
            4, TimeUnit.HOURS // Change this from 24 to 4
        )
            .addTag("widget_update_tag")
            .build()

        WorkManager.getInstance(applicationContext).enqueueUniquePeriodicWork(
            "daily_widget_update",
            ExistingPeriodicWorkPolicy.KEEP, // Crucial: Prevents duplicates
            periodicRequest
        )
    }
}