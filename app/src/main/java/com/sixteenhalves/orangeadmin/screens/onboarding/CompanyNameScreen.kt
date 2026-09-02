package com.sixteenhalves.orangeadmin.screens.onboarding

import androidx.compose.foundation.layout.Arrangement
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
fun CompanyNameScreen(
    onNext: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier.fillMaxSize().padding(16.dp),
        contentAlignment = Alignment.Center,
    ) {
        var companyName by remember { mutableStateOf("") }
        val companyNameValid = companyName.length >= 8

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween,
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    "What's your company name?",
                    style =
                        MaterialTheme
                            .typography.headlineSmall
                            .copy(fontWeight = FontWeight.SemiBold),
                )
                Text(
                    "This will be used to personalize your invoices. You can always change it later",
                    style = MaterialTheme.typography.bodyMedium,
                )

                Spacer(Modifier.height(32.dp))

                SharedTextField(
                    value = companyName,
                    onValueChange = { companyName = it },
                    placeHolderText = "e.g. Legal Company ltd.",
                )
            }
            Spacer(Modifier.height(64.dp))
            Button(
                onClick = { onNext() },
                modifier = Modifier.fillMaxWidth(),
                enabled = companyNameValid,
            ) {
                Text("Next")
            }
        }
    }
}
