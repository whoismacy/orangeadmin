package com.sixteenhalves.orangeadmin.screens.onboarding

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
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
import com.sixteenhalves.orangeadmin.domain.EventManager
import com.sixteenhalves.orangeadmin.viewmodels.AuthViewModel

@Composable
fun CompanyDescriptionScreen(
    authViewModel: AuthViewModel,
    onNext: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier.fillMaxSize().padding(16.dp),
        contentAlignment = Alignment.Center,
    ) {
        var companyDescription by remember { mutableStateOf("") }
        val companyDescriptionMinValid = companyDescription.split(" ").count() >= 10
        val companyDescriptionMaxValid = companyDescription.split("").count() < 500

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween,
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    "Tell us a little about your business",
                    style =
                        MaterialTheme
                            .typography.headlineSmall
                            .copy(fontWeight = FontWeight.SemiBold),
                )
                Text(
                    "Briefly describe what your company does",
                    style = MaterialTheme.typography.bodyMedium,
                )

                Spacer(Modifier.height(32.dp))

                OutlinedTextField(
                    value = companyDescription,
                    onValueChange = { companyDescription = it },
                    placeholder = { Text("e.g. A sales company that ............") },
                    modifier = Modifier.fillMaxWidth().heightIn(min = 400.dp),
                    enabled = companyDescriptionMaxValid,
                )
            }
            Spacer(Modifier.height(64.dp))
            Button(
                onClick = {
                    if (companyDescriptionMaxValid) {
                        authViewModel.updateCompanyDesc(companyDescription)
                        onNext()
                    } else {
                        EventManager.triggerEvent(EventManager.AppEvent.ShowEvent("Maximum word count (500) exceeded."))
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = companyDescriptionMinValid,
            ) {
                Text("Next")
            }
        }
    }
}
