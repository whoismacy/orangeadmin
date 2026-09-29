package com.sixteenhalves.orangeadmin.screens.onboarding

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.sixteenhalves.orangeadmin.domain.EventManager
import com.sixteenhalves.orangeadmin.viewmodels.AuthViewModel

@Composable
fun CompanyDescriptionScreen(
    authViewModel: AuthViewModel,
    onNext: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var companyDescription by remember { mutableStateOf("") }

    val wordCount = if (companyDescription.isBlank()) 0 else companyDescription.trim().split("\\s+".toRegex()).size
    val hasMinimumWords = wordCount >= 10
    val maxChars = 500

    Column(
        modifier =
            modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = "About your business",
                style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onBackground,
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Briefly describe what your company does. This helps us tailor the experience.",
                style =
                    MaterialTheme.typography.bodyMedium.copy(
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    ),
                textAlign = TextAlign.Center,
            )

            Spacer(modifier = Modifier.height(32.dp))

            OutlinedTextField(
                value = companyDescription,
                onValueChange = {
                    if (it.length <= maxChars) {
                        companyDescription = it
                    }
                },
                placeholder = { Text("e.g. A retail store specializing in high-quality electronics and accessories...") },
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                shape = RoundedCornerShape(12.dp),
                supportingText = {
                    val color = if (hasMinimumWords) MaterialTheme.colorScheme.onSurfaceVariant else MaterialTheme.colorScheme.error
                    Text(
                        text = "$wordCount/10 min words  •  ${companyDescription.length}/$maxChars chars",
                        color = color,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.End,
                    )
                },
                isError = wordCount > 0 && !hasMinimumWords,
            )
        }

        Button(
            onClick = {
                if (hasMinimumWords) {
                    authViewModel.updateCompanyDesc(companyDescription.trim())
                    onNext()
                } else {
                    EventManager.triggerEvent(EventManager.AppEvent.ShowEvent("Please enter at least 10 words."))
                }
            },
            modifier =
                Modifier
                    .fillMaxWidth()
                    .height(56.dp),
            shape = RoundedCornerShape(12.dp),
            enabled = hasMinimumWords,
        ) {
            Text(
                text = "Next",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold),
            )
        }
    }
}
