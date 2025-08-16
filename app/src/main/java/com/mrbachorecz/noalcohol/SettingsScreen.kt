package com.mrbachorecz.noalcohol

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(onClose: () -> Unit) {
    val backgroundColor = MaterialTheme.colorScheme.background
    val dividerColor = Color.Gray

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
    ) {
        TopAppBar(
            title = {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = Icons.Filled.Settings,
                        contentDescription = "Settings",
                        modifier = Modifier.size(32.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = "Settings",
                        style = MaterialTheme.typography.headlineMedium,
                        fontSize = 24.sp
                    )
                }
            },
            navigationIcon = {},
            actions = {
                IconButton(onClick = onClose) {
                    Icon(
                        imageVector = Icons.Filled.Close,
                        contentDescription = "Close"
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = backgroundColor
            )
        )
        val allowMorningNotification = remember { mutableStateOf(false) }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Allow morning notification",
                modifier = Modifier.weight(1f),
                color = MaterialTheme.colorScheme.onBackground
            )
            Switch(
                checked = allowMorningNotification.value,
                onCheckedChange = { allowMorningNotification.value = it }
            )
        }
        HorizontalDivider(
            color = dividerColor,
            thickness = 3.dp,
            modifier = Modifier.padding(horizontal = 8.dp)
        )
        // Add more rows here, each separated by a Divider
    }
}