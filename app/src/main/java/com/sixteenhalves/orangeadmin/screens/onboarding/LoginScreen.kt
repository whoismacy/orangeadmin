package com.sixteenhalves.orangeadmin.screens.onboarding

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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

@Composable
fun LoginScreen(modifier: Modifier = Modifier) {
    var emailAddress by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val emailAddressOnChange: (text: String) -> Unit = { emailAddress = it }
    val passwordOnChange: (text: String) -> Unit = { password = it }

    Box(modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Text("Welcome Back")
            Text("Enter your details to sign in")
        }

        Column {
            Column {
                Text("Email Address")
                SharedTextField(value = emailAddress, onValueChange = emailAddressOnChange)
            }
            Column {
                Text("Password")
                SharedTextField(value = password, onValueChange = passwordOnChange)
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
            ) {
                Text("Forgot Password?")
            }
        }

        Button(
            onClick = {},
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text("Login")
        }

        Row {
            Text("Don't have an account? ")
            Text(
                "Sign up",
                color = MaterialTheme.colorScheme.secondary,
                fontWeight = FontWeight.Bold,
            )
        }
    }
}
}

@Preview
@Composable
fun LoginScreenPreview() {
    LoginScreen()
}
