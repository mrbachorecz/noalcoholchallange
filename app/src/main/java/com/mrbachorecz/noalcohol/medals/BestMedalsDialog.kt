package com.mrbachorecz.noalcohol.medals

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties

@Composable
fun BestMedalsDialog(
    numberOfDays: Int,
    bestMedal: Int,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    val sortedMedals = remember { MEDALS.toList().sortedBy { (days, _) -> days } }
    val confirmedMedalDays =
        sortedMedals.lastOrNull { bestMedal >= it.first }?.first
    val currentMedalDays =
        sortedMedals.lastOrNull { numberOfDays >= it.first }?.first
    val actionNeeded =
        currentMedalDays != null &&
            (confirmedMedalDays == null || currentMedalDays > confirmedMedalDays)

    var showAchievementDialog by remember { mutableStateOf(false) }
    var achievementPrompted by remember(numberOfDays, bestMedal) { mutableStateOf(false) }

    LaunchedEffect(numberOfDays, bestMedal) {
        if (actionNeeded && !achievementPrompted) {
            showAchievementDialog = true
            achievementPrompted = true
        }
    }

    if (showAchievementDialog) {
        AlertDialog(
            onDismissRequest = {},
            title = {
                Text(
                    text = "New Achievement :)",
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            },
            text = {
                Text("You have a new medal. If you've forgot to restart progress, go back, else confirm.")
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        onConfirm()
                        showAchievementDialog = false
                    }
                ) {
                    Text("Confirm")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        showAchievementDialog = false
                        onDismiss()
                    }
                ) {
                    Text("Go Back")
                }
            },
            properties = DialogProperties(
                dismissOnBackPress = false,
                dismissOnClickOutside = false
            )
        )
    }

    val maxHeight = (LocalConfiguration.current.screenHeightDp * 0.7f).dp
    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(max = maxHeight),
            shape = MaterialTheme.shapes.extraLarge,
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(horizontal = 20.dp, vertical = 16.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Best Medal Ever",
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(16.dp))

                val currentMaxMedal =
                    if (bestMedal > numberOfDays) bestMedal else numberOfDays
                if (currentMaxMedal == 0) {
                    Text(
                        text = "No medal achieved yet.",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier
                            .padding(horizontal = 8.dp)
                            .alpha(0.7f),
                        textAlign = TextAlign.Center
                    )
                } else {
                    val currentBestMedal =
                        sortedMedals.lastOrNull { currentMaxMedal >= it.first }?.second
                    if (currentBestMedal != null) {
                        MedalIcon(currentBestMedal, iconSize = 150.dp)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = currentBestMedal.message,
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onBackground,
                            modifier = Modifier
                                .padding(horizontal = 8.dp)
                                .alpha(0.7f),
                            textAlign = TextAlign.Center
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    val currentMedal =
                        sortedMedals.lastOrNull { numberOfDays >= it.first }?.second
                    val nextMedal =
                        sortedMedals.firstOrNull { numberOfDays < it.first }?.second
                    CurrentAndNextMessage(currentMedal, nextMedal)
                }

                Spacer(modifier = Modifier.height(12.dp))
                TextButton(
                    onClick = onDismiss,
                    modifier = Modifier.align(Alignment.End)
                ) {
                    Text("Close")
                }
            }
        }
    }
}
