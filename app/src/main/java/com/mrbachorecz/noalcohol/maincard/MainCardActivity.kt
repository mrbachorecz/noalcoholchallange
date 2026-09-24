package com.mrbachorecz.noalcohol.maincard

import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.glance.appwidget.GlanceAppWidgetManager
import androidx.lifecycle.lifecycleScope
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.mrbachorecz.noalcohol.InitActivity
import com.mrbachorecz.noalcohol.initialdate.DatePickerActivity
import com.mrbachorecz.noalcohol.medals.BestMedalsActivity
import com.mrbachorecz.noalcohol.medals.MedalsActivity
import com.mrbachorecz.noalcohol.settings.SettingsActivity
import com.mrbachorecz.noalcohol.storage.readBestMedalEver
import com.mrbachorecz.noalcohol.storage.readLastDrinkingDate
import com.mrbachorecz.noalcohol.storage.writeLastDrinkingDate
import com.mrbachorecz.noalcohol.theme.UITheme
import com.mrbachorecz.noalcohol.widget.DailyWidgetWorker
import com.mrbachorecz.noalcohol.widget.DaysCounterWidget
import com.mrbachorecz.noalcohol.widget.DaysCounterWidgetReceiver
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class MainCardActivity : ComponentActivity() {

    private var maxMedalState by mutableIntStateOf(0)
    private var storedDateState by mutableStateOf("")
    private var daysPassedState by mutableIntStateOf(0)

    override fun onResume() {
        super.onResume()
        if (!refreshMainState()) return
        refreshWidget()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (!refreshMainState()) return

        setContent {
            UITheme {
                val context = LocalContext.current
                if (storedDateState.isNotEmpty()) {
                    MainCardScreen(
                        storedDate = storedDateState,
                        numberOfDays = daysPassedState,
                        maxMedal = maxMedalState,
                        onReset = {
                            // Clear date so canceling the picker cannot restore the old streak.
                            writeLastDrinkingDate(this@MainCardActivity, "")
                            startActivity(
                                Intent(this@MainCardActivity, DatePickerActivity::class.java)
                            )
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

    /** @return false if this activity is finishing (no valid date). */
    private fun refreshMainState(): Boolean {
        val date = readLastDrinkingDate(this)
        if (date.isEmpty() || DaysCalculator.parseStoredDate(date) == null) {
            if (date.isNotEmpty()) {
                writeLastDrinkingDate(this, "")
            }
            startActivity(Intent(this, InitActivity::class.java))
            finish()
            return false
        }
        storedDateState = date
        daysPassedState = DaysCalculator.calculateDaysPassed(date)
        maxMedalState = readBestMedalEver(this)
        return true
    }

    private fun refreshWidget() {
        lifecycleScope.launch {
            val context = applicationContext
            val manager = GlanceAppWidgetManager(context)
            val ids = manager.getGlanceIds(DaysCounterWidget::class.java)

            ids.forEach { id ->
                DaysCounterWidget().update(context, id)
            }

            val updateIntent = Intent(context, DaysCounterWidgetReceiver::class.java).apply {
                action = AppWidgetManager.ACTION_APPWIDGET_UPDATE
            }
            val appWidgetIds = AppWidgetManager.getInstance(context)
                .getAppWidgetIds(ComponentName(context, DaysCounterWidgetReceiver::class.java))

            updateIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds)
            context.sendBroadcast(updateIntent)
        }
    }

    private fun scheduleDailyWidgetUpdate() {
        val periodicRequest = PeriodicWorkRequestBuilder<DailyWidgetWorker>(
            4, TimeUnit.HOURS
        )
            .addTag("widget_update_tag")
            .build()

        WorkManager.getInstance(applicationContext).enqueueUniquePeriodicWork(
            "daily_widget_update",
            ExistingPeriodicWorkPolicy.KEEP,
            periodicRequest
        )
    }
}
