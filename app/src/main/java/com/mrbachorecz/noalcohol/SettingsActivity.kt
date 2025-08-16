package com.mrbachorecz.noalcohol

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.ui.platform.LocalContext

class SettingsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            UITheme {
                val context = LocalContext.current
                Column {
                    SettingsScreen(onClose = { (context as? ComponentActivity)?.finish() })
                }
            }
        }
    }
}