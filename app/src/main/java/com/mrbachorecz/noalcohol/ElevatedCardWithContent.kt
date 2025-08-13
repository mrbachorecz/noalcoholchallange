package com.mrbachorecz.noalcohol

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
    Card(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 12.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent,
            contentColor = Color.White
        )
    ) {
        Box(
            modifier = Modifier
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            Color(0xFF90CAF9),
                            Color(0xFF42A5F5)
                        )
                    )
                )
                .padding(24.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        }
    }
}