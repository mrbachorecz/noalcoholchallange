package com.mrbachorecz.noalcohol.medals

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MilitaryTech
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.mrbachorecz.noalcohol.theme.ThemeManager
import hexagonShape

val Gold = Color(0xFFFFD700)
val Silver = Color(0xFFC0C0C0)
val Bronze = Color(0xFFCD7F32)
val Platinum = Color(0xFFE5E4E2)

val MEDALS: Map<Int, MedalInfo> = mapOf(
    1 to MedalInfo(Color.White, "1 day", "white"),
    7 to MedalInfo(Bronze, "1 week", "bronze"),
    30 to MedalInfo(Silver, "1 month", "silver"),
    100 to MedalInfo(Gold, "100 days", "gold"),
    365 to MedalInfo(Platinum, "1 year", "platinum"),
)

data class MedalInfo(
    val color: Color,
    val message: String,
    val colorName: String
)

@Composable
fun MedalIcon(medal: MedalInfo, iconSize: Dp = 32.dp) {
    val backgroundCircleSize = iconSize + 16.dp
    val needsGrayBackground =
        ThemeManager.isLightTheme() || ThemeManager.isSystemTheme() && !isSystemInDarkTheme()
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(backgroundCircleSize)
            .background(
                color = if (needsGrayBackground) Color.DarkGray else Color.Transparent,
                shape = hexagonShape
            )
    ) {
        Icon(
            imageVector = Icons.Filled.MilitaryTech,
            contentDescription = medal.message,
            tint = medal.color,
            modifier = Modifier
                .width(iconSize)
                .height(iconSize)
        )
    }
}

@Composable
fun CurrentAndNextMessage(current: MedalInfo?, next: MedalInfo?) {
    when {
        current == null && next != null -> Row(verticalAlignment = Alignment.CenterVertically) {
            Text("Next medal: ${next.message} ")
            MedalIcon(next)
        }

        current != null && next == null -> Text("All medals earned!!!")
        current != null && next != null -> Row(verticalAlignment = Alignment.CenterVertically) {
            Text("Current: ")
            MedalIcon(current)
            Text(", next: ${next.message} ")
            MedalIcon(next)
            Text(".")
        }

        else -> Text("No medals earned yet.")
    }
}