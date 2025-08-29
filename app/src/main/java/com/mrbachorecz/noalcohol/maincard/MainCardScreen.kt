package com.mrbachorecz.noalcohol.maincard

import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.mrbachorecz.noalcohol.maincard.DaysCalculator.calculateDaysPassed
import kotlin.math.roundToInt


val greetings = listOf(
    "Welcome Back",
    "Hello",
    "Hey",
    "Welcome Aboard",
    "Good to See You",
    "Glad You're Here",
    "Hi there",
    "Great to Have You",
    "Stay Strong",
    "Proud of You",
    "Happy to See You",
)

val topAppBarHeight = 64.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainCardScreen(
    storedDate: String,
    onReset: () -> Unit,
    onMedalsClick: () -> Unit,
    onSettingsClick: () -> Unit
) {

    val randomGreeting = remember { greetings.random() }
    val randomQuote = remember { sobrietyQuotes.random() }

    val dividerColor = Color.Gray

    if (storedDate.isNotEmpty()) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = randomGreeting,
                            style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.primary,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                                .offset(x = (-22).dp)
                        )
                    },
                    navigationIcon = {
                        MainHamburgerMenu(onMedalsClick, onSettingsClick, onReset)
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.Transparent
                    ),
                    actions = {}
                )
            },
            bottomBar = {
                //BottomSubmitButton(text = "Reset", onSubmit = onReset)
            },
            containerColor = Color.Transparent
        ) { innerPadding ->
            val offsetY = remember { mutableFloatStateOf(0f) }
            val availableScreenWidth = LocalConfiguration.current.screenWidthDp.dp - 50.dp
            val availableScreenHeight =
                LocalConfiguration.current.screenHeightDp.dp - topAppBarHeight
            val circleSize = (availableScreenHeight.value * 0.28f).roundToInt()
            val cardTopOffset = ((availableScreenHeight.value * 0.5f - circleSize) / 2).roundToInt()

            val threshold = circleSize
            val swipeMax = cardTopOffset + threshold + 1f
            val dividerProgress = (offsetY.floatValue / swipeMax).coerceIn(0f, 1f)
            val dividerWidth = (dividerProgress * availableScreenWidth.value).dp

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                val daysPassed = calculateDaysPassed(storedDate)
                ElevatedCardWithContent(
                    text = "$daysPassed",
                    unit = if (daysPassed == 1) "DAY" else "DAYS",
                    Modifier
                        .size(circleSize.dp)
                        .align(Alignment.TopCenter)
                        .padding(all = 16.dp)
                        .offset {
                            IntOffset(
                                x = 0,
                                y = cardTopOffset + offsetY.floatValue.roundToInt()
                            )
                        }
                        .pointerInput(Unit) {
                            detectVerticalDragGestures(
                                onVerticalDrag = { change, dragAmount ->
                                    change.consume()
                                    val newOffset = offsetY.floatValue + dragAmount
                                    offsetY.floatValue =
                                        newOffset.coerceIn(
                                            0f,
                                            swipeMax
                                        ) // 100f is your threshold
                                },
                                onDragEnd = {
                                    if (offsetY.floatValue >= swipeMax) { // Set a threshold for a full swipe
                                        onReset()
                                    }
                                    // Reset the position
                                    offsetY.floatValue = 0f
                                },
                            )
                        },
                )

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxSize()
                        .offset(x = 0.dp, y = (cardTopOffset + threshold + 1f).dp + topAppBarHeight)
                ) {
                    HorizontalDivider(
                        color = dividerColor,
                        thickness = 3.dp,
                        modifier = Modifier
                            .padding(horizontal = 8.dp)
                            .width(dividerWidth)
                    )
                    Text(
                        text = "“$randomQuote”",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier
                            .padding(horizontal = 32.dp)
                            .alpha(0.7f),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}