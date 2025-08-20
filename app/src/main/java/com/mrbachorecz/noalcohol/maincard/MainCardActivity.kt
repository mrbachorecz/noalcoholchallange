package com.mrbachorecz.noalcohol.maincard

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.platform.LocalContext
import com.mrbachorecz.noalcohol.UITheme
import com.mrbachorecz.noalcohol.initialdate.DatePickerActivity
import com.mrbachorecz.noalcohol.medals.MedalsActivity
import com.mrbachorecz.noalcohol.settings.SettingsActivity
import com.mrbachorecz.noalcohol.storage.readLastDrinkingDate

class MainCardActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            UITheme {
                val context = LocalContext.current
                val storedDate = readLastDrinkingDate(context)

                if (storedDate.isNotEmpty()) {
                    MainCardScreen(
                        storedDate = storedDate,
                        onReset = {
                            startActivity(Intent(this, DatePickerActivity::class.java))
                            finish()
                        },
                        onMedalsClick = {
                            val intent = Intent(context, MedalsActivity::class.java)
                            context.startActivity(intent)
                        },
                        onSettingsClick = {
                            val intent = Intent(context, SettingsActivity::class.java)
                            context.startActivity(intent)
                        }
                    )
                }
            }
        }
    }
}