package com.mrbachorecz.noalcohol

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ElevatedCardWithContent(
    text: String,
) {
    val colorStart = MaterialTheme.colorScheme.primary
    val colorEnd = MaterialTheme.colorScheme.secondary

    ElevatedCard(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        shape = MaterialTheme.shapes.large,
        colors = CardDefaults.elevatedCardColors(
            containerColor = Color.Transparent,
            contentColor = MaterialTheme.colorScheme.onPrimary
        ),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 12.dp)
    ) {
        Box(
            modifier = Modifier
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(colorStart, colorEnd)
                    ),
                    shape = MaterialTheme.shapes.large
                )
                .padding(24.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = text,
                fontSize = 26.sp,
                fontWeight = FontWeight.ExtraBold,
                color = MaterialTheme.colorScheme.onPrimary,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}