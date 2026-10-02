package com.sixteenhalves.orangeadmin.screens.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun BranchLocationScreen(
    onComplete: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier =
            modifier
                .fillMaxSize()
                .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "Set Location",
            style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Tap the map to select the location for this branch.",
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            style = MaterialTheme.typography.bodyMedium,
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Mock Map View Placeholder
        Box(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .clip(MaterialTheme.shapes.large)
                    .background(MaterialTheme.colorScheme.surfaceVariant),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = "Map View Placeholder",
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                // TODO: Handle sending location and branch name info to the server
                onComplete()
            },
            modifier =
                Modifier
                    .fillMaxWidth()
                    .height(52.dp),
        ) {
            Text("Save Branch", style = MaterialTheme.typography.titleMedium)
        }
    }
}
