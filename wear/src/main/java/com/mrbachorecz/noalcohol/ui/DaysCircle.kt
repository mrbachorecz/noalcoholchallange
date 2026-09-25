package com.mrbachorecz.noalcohol.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material.Text
import kotlin.math.cos
import kotlin.math.sin

/** Phone-app primary circle blue. */
val DaysCircleBlue = Color(0xFF335889)

@Composable
fun DaysCircle(
    days: Int,
    modifier: Modifier = Modifier,
    size: Dp = 120.dp
) {
    val unit = if (days == 1) "DAY" else "DAYS"
    Box(
        modifier = modifier
            .size(size)
            .clip(CircleShape)
            .background(DaysCircleBlue),
        contentAlignment = Alignment.Center
    ) {
        CurvesOnTheCircle()
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "$days",
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Text(
                text = unit,
                fontSize = 12.sp,
                fontWeight = FontWeight.Light,
                color = Color.White
            )
        }
    }
}

/** Same decorative white arcs as the phone main-screen circle. */
@Composable
fun CurvesOnTheCircle() {
    Canvas(modifier = Modifier.fillMaxSize()) {
        val rotationDegrees = 15f
        val centerX = size.width / 2f
        val centerY = size.height / 2f
        val cardRadius = size.minDimension / 2f

        val baseArcStartAngleDeg = 235f
        val baseArcEndAngleDeg = 305f
        val arcStartAngleDeg = baseArcStartAngleDeg + rotationDegrees
        val arcEndAngleDeg = baseArcEndAngleDeg + rotationDegrees
        val arcSpanDeg = baseArcEndAngleDeg - baseArcStartAngleDeg
        val cpAngularDeviationFromMid = arcSpanDeg / 4f
        val cpRadialPosition = cardRadius * 0.75f

        fun point(angleDeg: Float, radius: Float): Pair<Float, Float> {
            val rad = Math.toRadians(angleDeg.toDouble())
            return radius * cos(rad).toFloat() + centerX to
                radius * sin(rad).toFloat() + centerY
        }

        val (startX, startY) = point(arcStartAngleDeg, cardRadius)
        val (endX, endY) = point(arcEndAngleDeg, cardRadius)
        val midAngleDeg = (arcStartAngleDeg + arcEndAngleDeg) / 2f
        val (c1X, c1Y) = point(midAngleDeg - cpAngularDeviationFromMid, cpRadialPosition)
        val (c2X, c2Y) = point(midAngleDeg + cpAngularDeviationFromMid, cpRadialPosition)

        drawPath(
            path = Path().apply {
                moveTo(startX, startY)
                cubicTo(c1X, c1Y, c2X, c2Y, endX, endY)
            },
            color = Color.White.copy(alpha = 0.45f),
            style = Stroke(width = 7f)
        )

        val mirrorArcStartAngleDeg =
            (360f - baseArcStartAngleDeg) + 2 * rotationDegrees
        val mirrorArcEndAngleDeg =
            (360f - baseArcEndAngleDeg) + 2 * rotationDegrees
        val (mirrorStartX, mirrorStartY) = point(mirrorArcStartAngleDeg, cardRadius)
        val (mirrorEndX, mirrorEndY) = point(mirrorArcEndAngleDeg, cardRadius)
        val mirrorMidAngleDeg = (mirrorArcStartAngleDeg + mirrorArcEndAngleDeg) / 2f
        val (mirrorC1X, mirrorC1Y) =
            point(mirrorMidAngleDeg + cpAngularDeviationFromMid, cpRadialPosition)
        val (mirrorC2X, mirrorC2Y) =
            point(mirrorMidAngleDeg - cpAngularDeviationFromMid, cpRadialPosition)

        drawPath(
            path = Path().apply {
                moveTo(mirrorStartX, mirrorStartY)
                cubicTo(mirrorC1X, mirrorC1Y, mirrorC2X, mirrorC2Y, mirrorEndX, mirrorEndY)
            },
            color = Color.White.copy(alpha = 0.45f),
            style = Stroke(width = 7f)
        )
    }
}
