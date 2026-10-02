package com.sixteenhalves.orangeadmin.screens.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
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
import com.sixteenhalves.orangeadmin.components.shared.SharedTextField

@Composable
fun BranchDeviceScreen(
    onSave: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var deviceName by remember { mutableStateOf("") }
    var deviceSerial by remember { mutableStateOf("") }

    Column(
        modifier =
            modifier
                .fillMaxSize()
                .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            text = "Add POS Device",
            style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Register a new POS device for this location.",
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            style = MaterialTheme.typography.bodyMedium,
        )

        Spacer(modifier = Modifier.height(32.dp))

        SharedTextField(
            value = deviceName,
            onValueChange = { deviceName = it },
            label = "Device Name",
            placeHolderText = "e.g. Front Register 1",
        )

        Spacer(modifier = Modifier.height(16.dp))

        SharedTextField(
            value = deviceSerial,
            onValueChange = { deviceSerial = it },
            label = "Serial Number",
            placeHolderText = "Enter serial number",
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = {
                // TODO: Save POS device logic
                onSave()
            },
            modifier =
                Modifier
                    .fillMaxWidth()
                    .height(52.dp),
            enabled = deviceName.isNotBlank() && deviceSerial.isNotBlank(),
        ) {
            Text("Register Device", style = MaterialTheme.typography.titleMedium)
        }
    }
}
