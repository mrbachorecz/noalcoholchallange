package com.mrbachorecz.noalcohol.settings

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.mrbachorecz.noalcohol.theme.ThemeSetting


@Composable
fun ThemeSettingsSection(
    currentThemeSetting: ThemeSetting,
    onThemeSettingChange: (ThemeSetting) -> Unit,
    modifier: Modifier = Modifier,
    dividerColor: Color = Color.Gray
) {
    val forceThemeOptionsEnabled = currentThemeSetting != ThemeSetting.SYSTEM
    val isSystemDark = isSystemInDarkTheme()

    Column(modifier = modifier) {
        HorizontalDivider(
            color = dividerColor,
            thickness = 3.dp,
        )
        Text(
            text = "Theme",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(start = 8.dp, top = 16.dp, bottom = 8.dp),
            color = MaterialTheme.colorScheme.onBackground
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            val currentSystemTheme = if (isSystemDark) "Dark" else "Light"
            Text(
                text = "Use system settings ($currentSystemTheme)",
                modifier = Modifier.weight(1f),
                color = MaterialTheme.colorScheme.onBackground
            )
            Switch(
                checked = currentThemeSetting == ThemeSetting.SYSTEM,
                onCheckedChange = { isChecked ->
                    val newSetting = if (isChecked) {
                        ThemeSetting.SYSTEM
                    } else {
                        if (isSystemDark) ThemeSetting.DARK else ThemeSetting.LIGHT
                    }
                    onThemeSettingChange(newSetting)
                }
            )
        }
        HorizontalDivider(
            color = dividerColor,
            thickness = 1.dp,
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Force theme",
                modifier = Modifier.weight(1f),
                color = if (forceThemeOptionsEnabled) MaterialTheme.colorScheme.onBackground else MaterialTheme.colorScheme.onSurface.copy(
                    alpha = 0.38f
                )
            )
        }
        ThemeSettingOption(
            text = "Light mode",
            selected = currentThemeSetting == ThemeSetting.LIGHT,
            enabled = forceThemeOptionsEnabled,
            onClick = { onThemeSettingChange(ThemeSetting.LIGHT) },
            modifier = Modifier.padding(horizontal = 8.dp)
        )
        ThemeSettingOption(
            text = "Dark mode",
            selected = currentThemeSetting == ThemeSetting.DARK,
            enabled = forceThemeOptionsEnabled,
            onClick = { onThemeSettingChange(ThemeSetting.DARK) },
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 12.dp)
        )
        HorizontalDivider(
            color = dividerColor,
            thickness = 3.dp,
        )
    }
}