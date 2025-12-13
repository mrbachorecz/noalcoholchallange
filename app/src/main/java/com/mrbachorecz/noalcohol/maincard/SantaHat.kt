package com.mrbachorecz.noalcohol.maincard

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import kotlin.io.path.moveTo

@Composable
fun SantaHat(
    modifier: Modifier = Modifier
) {
    Canvas(modifier = modifier) {
        val width = size.width
        val height = size.height

        // Colors
        val hatRed = Color(0xFFD32F2F)
        val pompomWhite = Color.White

        // 1. Draw the Red Cone (Main Hat Body)
        // We draw a path that curves slightly to look like fabric
        val hatPath = Path().apply {
            // Start at bottom left of the red part
            moveTo(width * 0.1f, height * 0.8f)
            // Curve to the tip (top right-ish)
            quadraticBezierTo(
                width * 0.4f, height * 0.1f, // Control point
                width * 0.85f, height * 0.2f // End point (tip)
            )
            // Curve back down to bottom right
            quadraticBezierTo(
                width * 0.7f, height * 0.5f, // Control point
                width * 0.9f, height * 0.85f // End point
            )
            close()
        }

        drawPath(
            path = hatPath,
            color = hatRed
        )

        // 2. Draw the White Band (Brim)
        // A rounded rectangle at the bottom, rotated slightly
        drawRoundRect(
            color = pompomWhite,
            topLeft = Offset(0f, height * 0.75f),
            size = Size(width * 0.8f, height * 0.25f),
            cornerRadius = CornerRadius(20f, 20f)
        )

        // 3. Draw the Pom-Pom (Ball at the tip)
        drawCircle(
            color = pompomWhite,
            radius = width * 0.12f,
            center = Offset(width * 0.85f, height * 0.2f)
        )
    }
}