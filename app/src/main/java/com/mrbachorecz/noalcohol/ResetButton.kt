package com.mrbachorecz.noalcohol

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ResetButton(
    onReset: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 32.dp)
            .navigationBarsPadding(),
        contentAlignment = Alignment.BottomCenter
    ) {
        SubmitButton(
            text = "Reset",
            onSubmit = onReset
        )
    }
}