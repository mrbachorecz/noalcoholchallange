package com.mrbachorecz.noalcohol.maincard

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun ElevatedCardWithContent(
    text: String,
    unit: String,
    circleSize: Dp,
) {
    Card(
        modifier = Modifier
            .size(circleSize)
            .padding(16.dp),
        shape = CircleShape,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primary
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clip(CircleShape) // This clips the curves to the circle shape
                .background(MaterialTheme.colorScheme.primary) // The main background color
        ) {
            // Draw the custom curves using Canvas
            Canvas(modifier = Modifier.fillMaxSize()) {
                val centerX = size.width / 2f
                val centerY = size.height / 2f
                val cardRadius = size.minDimension / 2f

                // --- Top Arc Definition ---
                val arcStartAngleDeg = 235f // (270 - 35)
                val arcEndAngleDeg = 305f   // (270 + 35)
                val arcSpanDeg = arcEndAngleDeg - arcStartAngleDeg

                val startX = cardRadius * cos(Math.toRadians(arcStartAngleDeg.toDouble())).toFloat() + centerX
                val startY = cardRadius * sin(Math.toRadians(arcStartAngleDeg.toDouble())).toFloat() + centerY
                val endX = cardRadius * cos(Math.toRadians(arcEndAngleDeg.toDouble())).toFloat() + centerX
                val endY = cardRadius * sin(Math.toRadians(arcEndAngleDeg.toDouble())).toFloat() + centerY

                // To make it look like an arc of a larger circle, we need to calculate
                // control points that approximate this.
                // A common formula for Bézier control points for a circular arc:
                // For an arc of angle 'alpha' (in radians) and radius R_of_arc,
                // the distance from endpoint to control point along tangent is k * R_of_arc
                // where k = (4/3) * tan(alpha/4)

                // We don't directly know R_of_arc or the true 'alpha' of the intersecting circle's segment.
                // But we can influence the "roundness" by how we place control points
                // relative to the chord (line between startX,startY and endX,endY).

                // Let's simplify: the control points need to be on the "inside" of the chord.
                // Their position determines the bulge.
                // To make it look like a segment of ONE other circle, the control points
                // should be somewhat symmetrical with respect to the perpendicular bisector of the chord.

                // Midpoint of the angular span of OUR arc on the card
                val midAngleDeg = (arcStartAngleDeg + arcEndAngleDeg) / 2f

                // How much the control points deviate angularly from this midpoint.
                // For a more "single arc" feel, this should be small.
                val cpAngularDeviationFromMid = arcSpanDeg / 4f // e.g., quarter of the arc's own span

                // Radial position of control points. This determines the depth of the "scoop".
                // Must be < cardRadius for an inward curve.
                // A value like 0.1 * cardRadius would mean very deep.
                // A value like 0.7 * cardRadius would mean shallower.
                val cpRadialPosition = cardRadius * 0.75f // Experiment with this! Lower = deeper.

                val c1AngleDeg = midAngleDeg - cpAngularDeviationFromMid
                val c1X = cpRadialPosition * cos(Math.toRadians(c1AngleDeg.toDouble())).toFloat() + centerX
                val c1Y = cpRadialPosition * sin(Math.toRadians(c1AngleDeg.toDouble())).toFloat() + centerY

                val c2AngleDeg = midAngleDeg + cpAngularDeviationFromMid
                val c2X = cpRadialPosition * cos(Math.toRadians(c2AngleDeg.toDouble())).toFloat() + centerX
                val c2Y = cpRadialPosition * sin(Math.toRadians(c2AngleDeg.toDouble())).toFloat() + centerY


                val pathTop = Path().apply {
                    moveTo(startX, startY)
                    cubicTo(c1X, c1Y, c2X, c2Y, endX, endY)
                }
                drawPath(
                    path = pathTop,
                    color = Color.White.copy(alpha = 0.45f),
                    style = Stroke(width = 7f)
                )

                // --- Mirrored Bottom Arc ---
                val mirrorArcStartAngleDeg = 360f - arcStartAngleDeg
                val mirrorArcEndAngleDeg = 360f - arcEndAngleDeg

                val mirrorStartX = cardRadius * cos(Math.toRadians(mirrorArcStartAngleDeg.toDouble())).toFloat() + centerX
                val mirrorStartY = cardRadius * sin(Math.toRadians(mirrorArcStartAngleDeg.toDouble())).toFloat() + centerY
                val mirrorEndX = cardRadius * cos(Math.toRadians(mirrorArcEndAngleDeg.toDouble())).toFloat() + centerX
                val mirrorEndY = cardRadius * sin(Math.toRadians(mirrorArcEndAngleDeg.toDouble())).toFloat() + centerY

                val mirrorMidAngleDeg = (mirrorArcStartAngleDeg + mirrorArcEndAngleDeg) / 2f
                // cpAngularDeviationFromMid remains the same

                val mirrorC1AngleDeg = mirrorMidAngleDeg + cpAngularDeviationFromMid // Note swapped sign due to mirroring
                val mirrorC1X = cpRadialPosition * cos(Math.toRadians(mirrorC1AngleDeg.toDouble())).toFloat() + centerX
                val mirrorC1Y = cpRadialPosition * sin(Math.toRadians(mirrorC1AngleDeg.toDouble())).toFloat() + centerY

                val mirrorC2AngleDeg = mirrorMidAngleDeg - cpAngularDeviationFromMid // Note swapped sign
                val mirrorC2X = cpRadialPosition * cos(Math.toRadians(mirrorC2AngleDeg.toDouble())).toFloat() + centerX
                val mirrorC2Y = cpRadialPosition * sin(Math.toRadians(mirrorC2AngleDeg.toDouble())).toFloat() + centerY


                val pathBottom = Path().apply {
                    moveTo(mirrorStartX, mirrorStartY)
                    cubicTo(mirrorC1X, mirrorC1Y, mirrorC2X, mirrorC2Y, mirrorEndX, mirrorEndY)
                }
                drawPath(
                    path = pathBottom,
                    color = Color.White.copy(alpha = 0.45f),
                    style = Stroke(width = 7f)
                )
            }

            // This is your original text content, placed on top of the drawing
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
                    text = unit,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Light,
                    color = Color.White
                )
            }
        }
    }
}