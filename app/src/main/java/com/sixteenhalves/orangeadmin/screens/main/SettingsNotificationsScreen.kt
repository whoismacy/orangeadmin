package com.sixteenhalves.orangeadmin.screens.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun SettingsNotificationsScreen(modifier: Modifier = Modifier) {
    // In a real app, these would be backed by Preferences/DataStore
    var lowStockAlerts by remember { mutableStateOf(true) }
    var notificationSound by remember { mutableStateOf(true) }
    var notificationVibration by remember { mutableStateOf(false) }

    Column(
        modifier =
            modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surface)
                .padding(horizontal = 24.dp, vertical = 32.dp),
    ) {
        Text(
            text = "Notifications",
            style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.Black),
            color = MaterialTheme.colorScheme.onSurface,
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Manage how Orange Admin alerts you.",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )

        Spacer(modifier = Modifier.height(32.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainerLow),
            elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        ) {
            Column(
                modifier = Modifier.padding(vertical = 12.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp) // Subtle spacing between items
            ) {
                NotificationToggle(
                    title = "Low Stock Alerts",
                    subtitle = "Get notified when inventory is running dangerously low.",
                    checked = lowStockAlerts,
                    onCheckedChange = { lowStockAlerts = it },
                )

                NotificationToggle(
                    title = "Notification Sound",
                    subtitle = "Play a sound when an alert is received.",
                    checked = notificationSound,
                    onCheckedChange = { notificationSound = it },
                    enabled = lowStockAlerts,
                )

                NotificationToggle(
                    title = "Vibration",
                    subtitle = "Vibrate the device when an alert is received.",
                    checked = notificationVibration,
                    onCheckedChange = { notificationVibration = it },
                    enabled = lowStockAlerts,
                )
            }
        }
    }
}

@Composable
private fun NotificationToggle(
    title: String,
    subtitle: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    enabled: Boolean = true,
) {
    Row(
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Column(
            modifier = Modifier.weight(1f).padding(end = 24.dp),
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.ExtraBold),
                color = if (enabled) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodyMedium,
                color = if (enabled) MaterialTheme.colorScheme.onSurfaceVariant else MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f),
            )
        }

        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            enabled = enabled,
        )
    }
}
