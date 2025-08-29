package com.mrbachorecz.noalcohol.maincard

import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.roundToInt

@Composable
fun ElevatedCardWithContent(
    text: String,
    onSwipeDown: () -> Unit
) {
    val threshold = 100f
    val offsetY = remember { mutableFloatStateOf(0f) }

    Card(
        modifier = Modifier
            .size(220.dp)
            .padding(16.dp)
            .offset { IntOffset(x = 0, y = offsetY.floatValue.roundToInt()) }
            .pointerInput(Unit) {
                detectVerticalDragGestures(
                    onVerticalDrag = { change, dragAmount ->
                        change.consume()
                        val newOffset = offsetY.floatValue + dragAmount
                        offsetY.floatValue =
                            newOffset.coerceIn(0f, threshold + 1f) // 100f is your threshold
                    },
                    onDragEnd = {
                        if (offsetY.floatValue > threshold) { // Set a threshold for a full swipe
                            onSwipeDown()
                        }
                        // Reset the position
                        offsetY.floatValue = 0f
                    }
                )
            },
        shape = CircleShape,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primary
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
        )
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = text,
                fontSize = 52.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Text(
                text = "DAYS",
                fontSize = 16.sp,
                fontWeight = FontWeight.Light,
                color = Color.White
            )
        }
    }
}