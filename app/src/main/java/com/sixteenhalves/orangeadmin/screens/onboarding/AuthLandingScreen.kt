package com.sixteenhalves.orangeadmin.screens.onboarding

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthLandingScreen(
    onCreateAccount: () -> Unit,
    onLogin: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier.fillMaxSize().padding(16.dp),
        contentAlignment = Alignment.Center,
    ) {
        Column(
            verticalArrangement = Arrangement.SpaceAround,
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            FlowRow {
                Text(
                    "Let's Make ",
                    style =
                        MaterialTheme
                            .typography.displayLarge
                            .copy(fontWeight = FontWeight.Bold),
                )
                Text(
                    "Managing your ",
                    style =
                        MaterialTheme
                            .typography.displayLarge
                            .copy(fontStyle = FontStyle.Italic, fontWeight = FontWeight.Light),
                )
                Text(
                    "Business easier! ",
                    style =
                        MaterialTheme
                            .typography.displayLarge
                            .copy(fontWeight = FontWeight.Bold),
                )
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(24.dp),
            ) {
                Button(
                    modifier = Modifier.fillMaxWidth().height(48.dp),
                    onClick = onCreateAccount,
                ) {
                    Text("Get Started")
                }
                TextButton(
                    modifier = Modifier.fillMaxWidth().height(48.dp),
                    onClick = onLogin,
                ) {
                    Text("Already a member")
                }
            }
        }
    }
}
