package com.mrbachorecz.noalcohol.maincard

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.mrbachorecz.noalcohol.healthimpact.HEALTH_IMPACTS
import com.mrbachorecz.noalcohol.maincard.DaysCalculator.calculateDaysPassed
import com.mrbachorecz.noalcohol.medals.MEDALS
import com.mrbachorecz.noalcohol.medals.MedalIcon
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
    maxMedal: Int,
    onReset: () -> Unit,
    onMedalsClick: () -> Unit,
    onSettingsClick: () -> Unit,
    onBestMedalsClick: () -> Unit,
) {

    val randomGreeting = remember { greetings.random() }
    val randomQuote = remember { sobrietyQuotes.random() }

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
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ) { innerPadding ->
            val availableScreenHeight =
                LocalConfiguration.current.screenHeightDp.dp - topAppBarHeight
            val circleSize = (availableScreenHeight.value * 0.28f).roundToInt().dp

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                val daysPassed = calculateDaysPassed(storedDate)

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Row(modifier = Modifier.padding(top = 24.dp)) {
                        ElevatedCardWithContent(
                            text = "$daysPassed",
                            unit = if (daysPassed == 1) "DAY" else "DAYS",
                            circleSize = circleSize,
                            onLongPress = onReset // Pass the onReset lambda here
                        )
                    }
                    Row(modifier = Modifier.padding(top = 32.dp)) {
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
                    // Add the two rectangular cards here
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                top = 32.dp,
                                start = 16.dp,
                                end = 16.dp
                            ), // Add padding as needed
                        horizontalArrangement = Arrangement.SpaceAround // This will place cards with space around them
                    ) {
                        // Left Card
                        val numberOfDays = calculateDaysPassed(storedDate)
                        Box(
                            modifier = Modifier
                                .weight(1f) // Each card takes equal space
                                .padding(end = 8.dp)
                        ) {
                            val sortedMedals =
                                MEDALS.toList().sortedBy { (days, _) -> days }
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable { onBestMedalsClick() },
                                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp) // Optional: Add elevation
                            ) {
                                Column(
                                    modifier = Modifier
                                        .padding(16.dp)
                                        .fillMaxWidth(), // Add padding inside the card
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(
                                        "Best Medal Ever",
                                        style = MaterialTheme.typography.titleMedium
                                    )
                                    val currentMaxMedal =
                                        if (maxMedal > numberOfDays) maxMedal else numberOfDays
                                    val currentMedal =
                                        sortedMedals.lastOrNull { currentMaxMedal >= it.first }?.second
                                    if (currentMedal != null) {
                                        MedalIcon(currentMedal)
                                        Text(
                                            currentMedal.message,
                                            style = MaterialTheme.typography.bodySmall
                                        )
                                    } else {
                                        Text("Welcome at Start")
                                    }
                                    // Add more content as needed
                                }
                            }
                            val confirmedMedal =
                                sortedMedals.lastOrNull { maxMedal >= it.first }?.first
                            val currentMedal =
                                sortedMedals.lastOrNull { numberOfDays >= it.first }?.first
                            if (numberOfDays > 0 && currentMedal != null && confirmedMedal != null && currentMedal > confirmedMedal) {
                                Canvas(
                                    modifier = Modifier
                                        .size(5.dp) // Size of the red dot
                                        .align(Alignment.TopEnd) // Align to top-right of the Box
                                        .offset(x = (-8).dp, y = 8.dp) // Adjust position slightly
                                ) {
                                    drawCircle(color = Color(0xFFC62828))
                                }
                            }
                        }

                        // Right Card
                        Card(
                            modifier = Modifier
                                .weight(1f) // Each card takes equal space
                                .padding(start = 8.dp), // Add padding between cards
                            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp) // Optional: Add elevation
                        ) {
                            Column(
                                modifier = Modifier.padding(16.dp), // Add padding inside the card
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text("Health impact", style = MaterialTheme.typography.titleMedium)
                                if (numberOfDays > 0) {
                                    val sortedImpacts = HEALTH_IMPACTS.toList().sortedBy { (days, _) -> days }
                                    val currentImpact =
                                        sortedImpacts.lastOrNull { numberOfDays >= it.first }?.second!!
                                    Text("After ${currentImpact.title}", style = MaterialTheme.typography.titleMedium)
                                    for (impact in currentImpact.impacts) {
                                        Text("- $impact", style = MaterialTheme.typography.bodyMedium)
                                    }
                                } else {
                                    Text("Welcome at Start, wait at least 24 hours")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}