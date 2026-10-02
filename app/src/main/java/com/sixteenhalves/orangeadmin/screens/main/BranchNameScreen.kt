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
fun BranchNameScreen(
    onNext: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    var branchName by remember { mutableStateOf("") }

    Column(
        modifier =
            modifier
                .fillMaxSize()
                .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            text = "Create a New Branch",
            style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Give your new branch a recognizable name.",
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            style = MaterialTheme.typography.bodyMedium,
        )

        Spacer(modifier = Modifier.height(32.dp))

        SharedTextField(
            value = branchName,
            onValueChange = { branchName = it },
            label = "Branch Name",
            placeHolderText = "e.g. Downtown Store",
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = { onNext(branchName) },
            modifier =
                Modifier
                    .fillMaxWidth()
                    .height(52.dp),
            enabled = branchName.isNotBlank(),
        ) {
            Text("Select Location", style = MaterialTheme.typography.titleMedium)
        }
    }
}
