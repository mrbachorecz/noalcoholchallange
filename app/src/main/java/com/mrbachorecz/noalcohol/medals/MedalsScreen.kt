package com.mrbachorecz.noalcohol.medals

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MilitaryTech
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.mrbachorecz.noalcohol.SubMenuTitleWithClose

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MedalsScreen(
    numberOfDays: Int,
    onClose: () -> Unit
) {
    val dividerColor = Color.Gray

    Scaffold(
        topBar = {
            SubMenuTitleWithClose(
                title = "Medals",
                imageVector = Icons.Filled.MilitaryTech,
                onClose = onClose
            )
        },
        bottomBar = {

        },
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            HorizontalDivider(
                color = dividerColor,
                thickness = 3.dp,
                modifier = Modifier.padding(horizontal = 8.dp)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                val sortedMedals = MEDALS.toList().sortedBy { (days, _) -> days }
                val currentMedal = sortedMedals.lastOrNull { numberOfDays >= it.first }?.second
                val nextMedal = sortedMedals.firstOrNull { numberOfDays < it.first }?.second
                CurrentAndNextMessage(currentMedal, nextMedal)
            }

            HorizontalDivider(
                color = dividerColor,
                thickness = 3.dp,
                modifier = Modifier.padding(horizontal = 8.dp)
            )

            MEDALS
                .toList()
                .sortedByDescending { (days, _) -> days }
                .filter { (days, _) -> numberOfDays >= days }
                .forEach { (_, medal) ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        MedalIcon(medal)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = medal.message,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    }
                }
        }
    }
}