package com.mrbachorecz.noalcohol

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

const val STORED_DATE_KEY = "storedDate"

class InitActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val prefs = getSharedPreferences("app_prefs", MODE_PRIVATE)
        val storedDate = prefs.getString(STORED_DATE_KEY, null)

        if (storedDate == null) {
            setContent {
                UITheme {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(bottom = 32.dp)
                            .navigationBarsPadding(),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = "Welcome to No Alcohol!",
                                fontSize = 32.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )
                            Spacer(modifier = Modifier.height(24.dp))
                            SubmitButton(
                                text = "Specify last drink date",
                                onSubmit = {
                                    startActivity(
                                        Intent(
                                            this@InitActivity,
                                            DatePickerActivity::class.java
                                        )
                                    )
                                    finish()
                                }
                            )
                        }
                    }
                }
            }
        } else {
            startActivity(Intent(this, MainCardActivity::class.java))
            finish()
        }
    }
}