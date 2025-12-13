package com.mrbachorecz.noalcohol.maincard

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.withFrameNanos
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import java.util.Calendar
import kotlin.math.cos
import kotlin.math.sin
import kotlin.random.Random

@Composable
fun ElevatedCardWithContent(
    text: String,
    unit: String,
    circleSize: Dp,
    onLongPress: () -> Unit
) {
    var isPressed by remember { mutableStateOf(false) }
    var progress by remember { mutableFloatStateOf(0f) }
    val animatedProgress by animateFloatAsState(targetValue = progress, label = "ProgressAnimation")

    // Check if it is December
    val isDecember = remember {
        Calendar.getInstance().get(Calendar.MONTH) == Calendar.DECEMBER
    }

    // State to control snow visibility (starts true if December, turns off after 2s)
    var showSnow by remember { mutableStateOf(isDecember) }

    // Animate the alpha of the snow layer
    // When showSnow becomes false, this will animate from 1f to 0f over 1 second (1000ms)
    val snowAlpha by animateFloatAsState(
        targetValue = if (showSnow) 1f else 0f,
        animationSpec = tween(durationMillis = 1000),
        label = "SnowFade"
    )

    // Duration for the long press to trigger the reset
    val longPressDurationMillis = 650L // 0.65 seconds

    LaunchedEffect(isPressed) {
        if (isPressed) {
            val startTime = System.currentTimeMillis()
            while (isPressed && (System.currentTimeMillis() - startTime) < longPressDurationMillis) {
                progress =
                    (System.currentTimeMillis() - startTime).toFloat() / longPressDurationMillis
                delay(16) // Update roughly every frame
            }
            if (isPressed && (System.currentTimeMillis() - startTime) >= longPressDurationMillis) {
                onLongPress()
                progress = 0f // Reset progress after action
            }
            // If finger is lifted before duration, reset progress
            if (!isPressed) {
                progress = 0f
            }
        } else {
            progress = 0f
        }
    }

    // --- NEW: Wrap everything in a Box so we can layer the Hat on top ---
    Box(contentAlignment = Alignment.Center) {

        // This is your existing Card logic
        Card(
            modifier = Modifier
                .size(circleSize)
                .padding(16.dp)
                .pointerInput(Unit) {
                    detectTapGestures(
                        onPress = {
                            isPressed = true
                            tryAwaitRelease() // Wait until the press is released
                            isPressed = false
                        },
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
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primary)
            ) {
                // Draw the custom curves using Canvas
                CurvesOnTheCircle()

                // Draw Snowflakes if alpha is > 0
                if (snowAlpha > 0f) {
                    SnowfallEffect(alphaMultiplier = snowAlpha)
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

                if (isPressed && progress > 0f) {
                    CircularProgressIndicator(
                        progress = { animatedProgress },
                        modifier = Modifier.size(circleSize + 16.dp), // Make progress slightly larger than card
                        color = MaterialTheme.colorScheme.secondary,
                        trackColor = MaterialTheme.colorScheme.surfaceVariant, // Or any other color for the background track
                        strokeWidth = 4.dp,
                        strokeCap = StrokeCap.Round
                    )
                }
            }
        }

        // --- NEW: The Santa Hat ---
        // We place it only if it's December.
        // We align it to TopStart (Top Left) and offset it slightly to sit on the "rim".
        if (isDecember) {
            SantaHat(
                modifier = Modifier
                    // Size of the hat relative to the card. Adjust 60.dp as needed.
                    .size(circleSize / 3.5f)
                    .align(Alignment.TopStart)
                    // Nudge it slightly inwards and downwards to sit on the circle edge
                    .padding(start = 10.dp, top = 4.dp)
                    // Rotate it slightly for style
                    .graphicsLayer(rotationZ = -15f)
            )
        }
    }
}

// Data class to hold snowflake state
private data class Snowflake(
    var x: Float,
    var y: Float,
    val speed: Float,
    val radius: Float,
    val alpha: Float
)

@Composable
fun SnowfallEffect(alphaMultiplier: Float) {
    // Generate initial snowflakes
    val snowflakes = remember {
        mutableStateListOf<Snowflake>().apply {
            repeat(40) {
                add(
                    Snowflake(
                        x = Random.nextFloat(), // 0.0 to 1.0 (relative width)
                        y = Random.nextFloat() * -1f, // Start above the screen
                        speed = Random.nextFloat() * 0.01f + 0.005f, // Speed varies
                        radius = Random.nextFloat() * 4f + 2f,
                        alpha = Random.nextFloat() * 0.5f + 0.3f
                    )
                )
            }
        }
    }

    var frameTime by remember { mutableLongStateOf(0L) }

    // Animation Loop
    LaunchedEffect(Unit) {
        while (true) {
            withFrameNanos {
                frameTime = it // Trigger recomposition
                snowflakes.forEach { flake ->
                    flake.y += flake.speed
                    // If flake goes off bottom, reset to top
                    if (flake.y > 1.0f) {
                        flake.y = -0.1f
                        flake.x = Random.nextFloat()
                    }
                }
            }
        }
    }

    Canvas(modifier = Modifier.fillMaxSize().alpha(alphaMultiplier)) {
        // Read frameTime to force redraw on every frame
        frameTime

        snowflakes.forEach { flake ->
            drawCircle(
                color = Color.White.copy(alpha = flake.alpha),
                radius = flake.radius,
                center = Offset(
                    x = flake.x * size.width,
                    y = flake.y * size.height
                )
            )
        }
    }
}

@Composable
fun CurvesOnTheCircle() {
    Canvas(modifier = Modifier.fillMaxSize()) {
        val rotationDegrees = 15f // Adjust this value to rotate the arcs
        val centerX = size.width / 2f
        val centerY = size.height / 2f
        val cardRadius = size.minDimension / 2f

        // --- Top Arc Definition ---
        // Base angles (before rotation)
        val baseArcStartAngleDeg = 235f
        val baseArcEndAngleDeg = 305f

        // Apply rotation
        val arcStartAngleDeg = baseArcStartAngleDeg + rotationDegrees
        val arcEndAngleDeg = baseArcEndAngleDeg + rotationDegrees

        val arcSpanDeg = baseArcEndAngleDeg - baseArcStartAngleDeg // Span remains constant

        val startX =
            cardRadius * cos(Math.toRadians(arcStartAngleDeg.toDouble())).toFloat() + centerX
        val startY =
            cardRadius * sin(Math.toRadians(arcStartAngleDeg.toDouble())).toFloat() + centerY
        val endX = cardRadius * cos(Math.toRadians(arcEndAngleDeg.toDouble())).toFloat() + centerX
        val endY = cardRadius * sin(Math.toRadians(arcEndAngleDeg.toDouble())).toFloat() + centerY

        val midAngleDeg = (arcStartAngleDeg + arcEndAngleDeg) / 2f
        val cpAngularDeviationFromMid = arcSpanDeg / 4f
        val cpRadialPosition = cardRadius * 0.75f // Your preferred value

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
        // Apply rotation to base mirrored angles
        val baseMirrorArcStartAngleDeg = 360f - baseArcStartAngleDeg
        val baseMirrorArcEndAngleDeg = 360f - baseArcEndAngleDeg

        val mirrorArcStartAngleDeg =
            baseMirrorArcStartAngleDeg + 2 * rotationDegrees // Subtract for visual mirroring
        val mirrorArcEndAngleDeg =
            baseMirrorArcEndAngleDeg + 2 * rotationDegrees     // Subtract for visual mirroring


        val mirrorStartX =
            cardRadius * cos(Math.toRadians(mirrorArcStartAngleDeg.toDouble())).toFloat() + centerX
        val mirrorStartY =
            cardRadius * sin(Math.toRadians(mirrorArcStartAngleDeg.toDouble())).toFloat() + centerY
        val mirrorEndX =
            cardRadius * cos(Math.toRadians(mirrorArcEndAngleDeg.toDouble())).toFloat() + centerX
        val mirrorEndY =
            cardRadius * sin(Math.toRadians(mirrorArcEndAngleDeg.toDouble())).toFloat() + centerY

        val mirrorMidAngleDeg = (mirrorArcStartAngleDeg + mirrorArcEndAngleDeg) / 2f

        // Control point deviation remains the same in magnitude
        val mirrorC1AngleDeg = mirrorMidAngleDeg + cpAngularDeviationFromMid
        val mirrorC2AngleDeg = mirrorMidAngleDeg - cpAngularDeviationFromMid

        val mirrorC1X =
            cpRadialPosition * cos(Math.toRadians(mirrorC1AngleDeg.toDouble())).toFloat() + centerX
        val mirrorC1Y =
            cpRadialPosition * sin(Math.toRadians(mirrorC1AngleDeg.toDouble())).toFloat() + centerY
        val mirrorC2X =
            cpRadialPosition * cos(Math.toRadians(mirrorC2AngleDeg.toDouble())).toFloat() + centerX
        val mirrorC2Y =
            cpRadialPosition * sin(Math.toRadians(mirrorC2AngleDeg.toDouble())).toFloat() + centerY

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
}
