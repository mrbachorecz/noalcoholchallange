package com.mrbachorecz.noalcohol.widget

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.GlanceTheme
import androidx.glance.action.actionStartActivity
import androidx.glance.action.clickable
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.provideContent
import androidx.glance.background
import androidx.glance.layout.*
import androidx.glance.text.FontWeight
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import androidx.glance.appwidget.cornerRadius
import com.mrbachorecz.noalcohol.InitActivity

import androidx.glance.appwidget.GlanceAppWidgetReceiver
import androidx.glance.unit.ColorProvider
import com.mrbachorecz.noalcohol.maincard.DaysCalculator.calculateDaysPassed
import com.mrbachorecz.noalcohol.storage.readLastDrinkingDate

class DaysCounterWidgetReceiver : GlanceAppWidgetReceiver() {
    override val glanceAppWidget: GlanceAppWidget = DaysCounterWidget()
}

class DaysCounterWidget : GlanceAppWidget() {

    override suspend fun provideGlance(context: Context, id: GlanceId) {
        val daysCount = getDaysFromPrefs(context)

        provideContent {
            GlanceTheme {
                CounterWidgetContent(daysCount)
            }
        }
    }

    @Composable
    private fun CounterWidgetContent(days: Int) {
        val whiteColor = ColorProvider(Color.White)
        val customBlueBackground = ColorProvider(Color(0xFF335889))

        // Outer Box fills the 2x2 area to provide a centering container
        Box(
            modifier = GlanceModifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            // Inner Box is the Circle
            // 110dp is the standard 'safe' maximum for 2x2 cells to avoid clipping
            Box(
                modifier = GlanceModifier
                    .size(110.dp)
                    .background(customBlueBackground)
                    .cornerRadius(55.dp) // Half of size = Perfect Circle
                    .clickable(actionStartActivity<InitActivity>()),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = days.toString(),
                        style = TextStyle(
                            color = whiteColor,
                            fontSize = 36.sp,
                            fontWeight = FontWeight.Bold
                        )
                    )
                    Text(
                        text = "DAYS",
                        style = TextStyle(
                            color = whiteColor,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium
                        )
                    )
                }
            }
        }
    }

    private fun getDaysFromPrefs(context: Context): Int {
        val storedDate = readLastDrinkingDate(context)
        return calculateDaysPassed(storedDate)
    }
}