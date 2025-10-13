package com.mrbachorecz.noalcohol

import android.content.Intent
import android.os.Bundle
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
import com.mrbachorecz.noalcohol.initialdate.DatePickerActivity
import com.mrbachorecz.noalcohol.maincard.MainCardActivity
import com.mrbachorecz.noalcohol.notifications.NotificationPermissionUtils
import com.mrbachorecz.noalcohol.storage.readLastDrinkingDate
import com.mrbachorecz.noalcohol.submitbutton.SubmitButton
import com.mrbachorecz.noalcohol.theme.UITheme

class InitActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val context = this
        val storedDate = readLastDrinkingDate(context)

        if (storedDate.isEmpty()) {
            val notificationRequestPermissionLauncher = NotificationPermissionUtils
                .createRequestPermissionLauncher(this) { isGranted -> }
            NotificationPermissionUtils
                .checkAndRequestNotificationPermission(this, notificationRequestPermissionLauncher)
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
                                text = "Start Now",
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

// @Composable
// fun PermissionRationaleDialog(onConfirm: () -> Unit, onDismiss: () -> Unit) {
//     AlertDialog(
//         onDismissRequest = onDismiss,
//         title = { Text("Allow Background Activity") },
//         text = { Text("To ensure your sobriety timer and milestone notifications are always accurate, please allow the app to run in the background. This prevents the system from stopping it.") },
//         confirmButton = {
//             TextButton(onClick = onConfirm) {
//                 Text("OK")
//             }
//         },
//         dismissButton = {
//             TextButton(onClick = onDismiss) {
//                 Text("Cancel")
//             }
//         }
//     )
// }