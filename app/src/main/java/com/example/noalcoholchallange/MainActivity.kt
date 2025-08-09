package com.example.noalcoholchallange

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.edit
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val prefs = getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
            var storedDate by remember { mutableStateOf(prefs.getString("start_date", null)) }
            var inputDate by remember { mutableStateOf("") }
            val formatter = DateTimeFormatter.ISO_LOCAL_DATE

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize(Alignment.Center)
            ) {

                if (storedDate == null) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("Enter start date (YYYY-MM-DD):")
                        TextField(
                            value = inputDate,
                            onValueChange = { inputDate = it },
                            modifier = Modifier.fillMaxWidth()
                        )
                        Button(
                            onClick = {
                                // Validate and store date
                                try {
                                    LocalDate.parse(inputDate, formatter)
                                    prefs.edit { putString("start_date", inputDate) }
                                    storedDate = inputDate
                                } catch (_: Exception) { /* handle invalid date */
                                }
                            },
                            modifier = Modifier.padding(top = 8.dp)
                        ) {
                            Text("Save Date")
                        }
                    }
                } else {
                    val startDate = LocalDate.parse(storedDate, formatter)
                    val daysPassed = ChronoUnit.DAYS.between(startDate, LocalDate.now())
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Card(
                            modifier = Modifier
                                .padding(16.dp)
                                .fillMaxWidth(),
                            shape = MaterialTheme.shapes.medium,
                            elevation = CardDefaults.cardElevation(defaultElevation = 12.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = Color.Transparent, // Use transparent to show gradient
                                contentColor = Color.White
                            )
                        ) {
                            Box(
                                modifier = Modifier
                                    .background(
                                        brush = Brush.linearGradient(
                                            colors = listOf(
                                                Color(0xFF90CAF9), // Light blue
                                                Color(0xFF42A5F5)  // Slightly deeper blue for shine
                                            )
                                        )
                                    )
                                    .padding(24.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    "No Alcohol: $daysPassed days",
                                    fontSize = 24.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White,
                                    modifier = Modifier.fillMaxWidth(),
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    }
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 32.dp),
                contentAlignment = Alignment.BottomCenter
            ) {
                Button(
                    onClick = {
                        prefs.edit { remove("start_date") }
                        storedDate = null
                        inputDate = ""
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    ),
                    shape = MaterialTheme.shapes.large,
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 8.dp)
                ) {
                    Text("Reset", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                }
            }
        }
    }
}