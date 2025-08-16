package com.mrbachorecz.noalcohol

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

class MainCardActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            UITheme {
                val prefs = getSharedPreferences("app_prefs", MODE_PRIVATE)
                val storedDate = prefs.getString(STORED_DATE_KEY, "")

                if (storedDate != null && storedDate.isNotEmpty()) {
                    MainCardScreen(
                        storedDate = storedDate,
                        onReset = {
                            startActivity(Intent(this, DatePickerActivity::class.java))
                            finish()
                        },
                        onSettingsClick = { /* TODO: Navigate to settings */ }
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainCardScreen(
    storedDate: String?,
    onReset: () -> Unit,
    onSettingsClick: () -> Unit
) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    if (storedDate != null && storedDate.isNotEmpty()) {
        ModalNavigationDrawer(
            drawerContent = {
                ModalDrawerSheet {
                    NavigationDrawerItem(
                        label = { Text("Settings") },
                        selected = false,
                        onClick = {
                            scope.launch { drawerState.close() }
                            onSettingsClick()
                        }
                    )
                }
            },
            drawerState = drawerState,
        ) {
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = {},
                        navigationIcon = {
                            IconButton(onClick = { scope.launch { drawerState.open() } }) {
                                Icon(Icons.Filled.Menu, contentDescription = "Menu")
                            }
                        },
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = Color.Transparent
                        ),
                        actions = {}
                    )
                },
                bottomBar = {
                    Box(
                        Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                            .navigationBarsPadding(),
                        contentAlignment = Alignment.Center
                    ) {
                        SubmitButton(text = "Reset", onSubmit = onReset)
                    }
                },
                containerColor = Color.Transparent
            ) { innerPadding ->
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    contentAlignment = Alignment.Center
                ) {
                    ElevatedCardWithContent(calculateDaysPassed(storedDate))
                }
            }
        }
    }
}