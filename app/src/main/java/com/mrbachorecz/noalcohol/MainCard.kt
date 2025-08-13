package com.mrbachorecz.noalcohol

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

@Composable
fun MainCard(
    storedDate: String,
) {
    val formatter = DateTimeFormatter.ISO_LOCAL_DATE

    Surface(
        color = MaterialTheme.colorScheme.background,
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .wrapContentSize(Alignment.Center)
        ) {
            val startDate = LocalDate.parse(storedDate, formatter)
            val daysPassed = ChronoUnit.DAYS.between(startDate, LocalDate.now())
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                ElevatedCardWithContent("No Alcohol: $daysPassed days")
            }
        }
    }
}