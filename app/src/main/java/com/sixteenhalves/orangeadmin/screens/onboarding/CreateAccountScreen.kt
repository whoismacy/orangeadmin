package com.sixteenhalves.orangeadmin.screens.onboarding

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sixteenhalves.orangeadmin.components.shared.SharedTextField

// set validation for both name and email before submission
@Composable
fun CreateAccountScreen(modifier: Modifier = Modifier) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }

    val onNameChanged: (String) -> Unit = { name = it }
    val onEmailChanged: (String) -> Unit = { email = it }

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Text(
                "Create your account",
                style =
                    MaterialTheme
                        .typography.displaySmall
                        .copy(fontWeight = FontWeight.Bold),
            )
            Text("Let's get you started. Fill in your details below.")
        }
        Column() {

        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Text("Full Name")
            SharedTextField(value = name, onValueChange = onNameChanged)
        }
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Text("Email address")
            SharedTextField(value = email, onValueChange = onEmailChanged)
        }
        }
        Button(onClick = {}, modifier = Modifier.fillMaxWidth()) {
            Text("Next")
        }
    }
}

@Preview
@Composable
fun CreateAccountPreview() {
    CreateAccountScreen()
}
