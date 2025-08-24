package com.mrbachorecz.noalcohol.maincard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MilitaryTech
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.mrbachorecz.noalcohol.maincard.DaysCalculator.calculateDaysPassedMessage
import com.mrbachorecz.noalcohol.submitbutton.BottomSubmitButton

private val sobrietyQuotes = listOf(
    "The first step towards getting somewhere is to decide you’re not going to stay where you are.",
    "One day at a time.",
    "Your best days are ahead of you.",
    "Recovery is not for people who need it, it’s for people who want it.",
    "It does not matter how slowly you go as long as you do not stop. — Confucius",
    "Small steps every day.",
    "You are stronger than you think.",
    "Sobriety is a journey, not a destination.",
    "Believe you can and you’re halfway there. — Theodore Roosevelt",
    "Progress, not perfection.",
    "Every day is a new beginning.",
    "The only way out is through.",
    "You don’t have to see the whole staircase, just take the first step. — Martin Luther King Jr.",
    "Difficult roads often lead to beautiful destinations.",
    "Stay patient and trust your journey."
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainCardScreen(
    storedDate: String,
    onReset: () -> Unit,
    onMedalsClick: () -> Unit,
    onSettingsClick: () -> Unit
) {
    val menuExpanded = remember { mutableStateOf(false) }
    val randomQuote = remember { sobrietyQuotes.random() }

    if (storedDate.isNotEmpty()) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {},
                    navigationIcon = {
                        Box {
                            IconButton(onClick = { menuExpanded.value = true }) {
                                Icon(Icons.Filled.Menu, contentDescription = "Menu")
                            }
                            DropdownMenu(
                                expanded = menuExpanded.value,
                                onDismissRequest = { menuExpanded.value = false }
                            ) {
                                DropdownMenuItem(
                                    text = {
                                        Box(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(vertical = 4.dp),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Row(verticalAlignment = Alignment.CenterVertically) {
                                                Icon(
                                                    imageVector = Icons.Filled.MilitaryTech,
                                                    contentDescription = "Medals",
                                                    tint = MaterialTheme.colorScheme.onSurface
                                                )
                                                Spacer(modifier = Modifier.width(8.dp))
                                                Text(
                                                    text = "Medals",
                                                    color = MaterialTheme.colorScheme.onSurface
                                                )
                                            }
                                        }
                                    },
                                    onClick = {
                                        menuExpanded.value = false
                                        onMedalsClick()
                                    }
                                )
                                DropdownMenuItem(
                                    text = {
                                        Box(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(vertical = 4.dp),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Row(verticalAlignment = Alignment.CenterVertically) {
                                                Icon(
                                                    imageVector = Icons.Filled.Settings,
                                                    contentDescription = "Settings",
                                                    tint = MaterialTheme.colorScheme.onSurface
                                                )
                                                Spacer(modifier = Modifier.width(8.dp))
                                                Text(
                                                    text = "Settings",
                                                    color = MaterialTheme.colorScheme.onSurface
                                                )
                                            }
                                        }
                                    },
                                    onClick = {
                                        menuExpanded.value = false
                                        onSettingsClick()
                                    }
                                )
                            }
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.Transparent
                    ),
                    actions = {}
                )
            },
            bottomBar = {
                BottomSubmitButton(text = "Reset", onSubmit = onReset)
            },
            containerColor = Color.Transparent
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    ElevatedCardWithContent(calculateDaysPassedMessage(storedDate))
                    Spacer(modifier = Modifier.padding(16.dp))
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