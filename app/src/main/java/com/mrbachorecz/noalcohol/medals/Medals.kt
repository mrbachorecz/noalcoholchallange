package com.mrbachorecz.noalcohol.medals

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MilitaryTech
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.mrbachorecz.noalcohol.theme.ThemeManager
import hexagonShape

val Gold = Color(0xFFFFD700)
val Silver = Color(0xFFC0C0C0)
val Bronze = Color(0xFFCD7F32)
val Platinum = Color(0xFFE5E4E2)
val Diamond = Color(0xFFB9F2FF)

val MEDALS: Map<Int, MedalInfo> = mapOf(
    1 to MedalInfo(Color.White, "1 day", "white"),
    7 to MedalInfo(Bronze, "1 week", "bronze"),
    30 to MedalInfo(Silver, "1 month", "silver"),
    100 to MedalInfo(Gold, "100 days", "gold"),
    365 to MedalInfo(Platinum, "1 year", "platinum"),
    1000 to MedalInfo(Diamond, "1000 days", "diamond"),
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
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when {
            current == null && next == null -> {
                Text(
                    text = "No medals earned yet.",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }

            current != null && next == null -> {
                MedalStatusRow(label = "Current", medal = current)
                Text(
                    text = "All medals earned!",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary,
                    textAlign = TextAlign.Center
                )
            }

            else -> {
                if (current != null) {
                    MedalStatusRow(label = "Current", medal = current)
                }
                if (next != null) {
                    MedalStatusRow(label = "Next", medal = next)
                }
            }
        }
    }
}

@Composable
private fun MedalStatusRow(label: String, medal: MedalInfo) {
    val iconColumnWidth = 48.dp
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.width(80.dp)
        )
        Box(
            modifier = Modifier.width(iconColumnWidth),
            contentAlignment = Alignment.Center
        ) {
            MedalIcon(medal, iconSize = 28.dp)
        }
        Text(
            text = medal.message,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier
                .weight(1f)
                .padding(start = 8.dp)
        )
    }
}

