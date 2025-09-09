package com.mrbachorecz.noalcohol.medals

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BestMedalsScreen(
    numberOfDays: Int,
    bestMedal: Int,
    confirm: () -> Unit,
    onClose: () -> Unit
) {
    var showDialog by remember { mutableStateOf(false) }
    // Use a state to track if the dialog has already been triggered for the current condition
    var dialogTriggered by remember(numberOfDays, bestMedal) { mutableStateOf(false) }

    // Use LaunchedEffect to show the dialog when the condition is met
    // and the dialog hasn't been shown yet for this specific state.
    LaunchedEffect(numberOfDays, bestMedal) {
        if (numberOfDays > bestMedal && !dialogTriggered) {
            showDialog = true
            dialogTriggered = true // Mark that the dialog has been triggered for this condition
        }
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = {
                // This is typically called when the user presses back or clicks outside.
                // Since we force button interaction with properties,
                // this might not be strictly necessary, but good practice.
                // If you want to allow dismissing without choosing, you'd handle it here.
                // showDialog = false
                // onUserResponse(false) // Or some default action
            },
            title = {
                Text(
                    text = "New Achievement :)", textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            },
            text = {
                Text("You have a new medal. If you've forgot to restart progress, go back, else confirm.")
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        confirm()
                        showDialog = false
                    }
                ) {
                    Text("Confirm")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        onClose()
                    }
                ) {
                    Text("Go Back")
                }
            },
            // Prevent dismissing without choosing an option
            properties = DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false)
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Best Medal Ever",
                        style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                },
                navigationIcon = {

                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent
                ),
                actions = {
                    IconButton(onClick = onClose) {
                        Icon(
                            imageVector = Icons.Filled.Close,
                            contentDescription = "Close"
                        )
                    }
                }
            )
        },
        bottomBar = {

        },
        containerColor = MaterialTheme.colorScheme.primaryContainer
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val currentMaxMedal =
                if (bestMedal > numberOfDays) bestMedal else numberOfDays
            if (currentMaxMedal == 0) {
                Text(
                    text = "No medal achieved yet.",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier
                        .padding(horizontal = 32.dp)
                        .alpha(0.7f),
                    textAlign = TextAlign.Center
                )
            } else {
                val sortedMedals = MEDALS.toList().sortedBy { (days, _) -> days }
                val currentBestMedal =
                    sortedMedals.lastOrNull { currentMaxMedal >= it.first }?.second
                MedalIcon(currentBestMedal!!, iconSize = 150.dp)
                Text(
                    text = currentBestMedal.message,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier
                        .padding(horizontal = 32.dp)
                        .alpha(0.7f),
                    textAlign = TextAlign.Center
                )
                val currentMedal =
                    sortedMedals.lastOrNull { numberOfDays >= it.first }?.second
                val nextMedal = sortedMedals.firstOrNull { numberOfDays < it.first }?.second
                CurrentAndNextMessage(currentMedal, nextMedal)
            }
        }
    }
}