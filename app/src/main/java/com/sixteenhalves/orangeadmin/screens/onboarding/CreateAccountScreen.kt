package com.sixteenhalves.orangeadmin.screens.onboarding

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sixteenhalves.orangeadmin.components.shared.SharedTextField

// Improved Create Account screen: card layout, labeled fields, validation and polished spacing
@Composable
fun CreateAccountScreen(
    modifier: Modifier = Modifier,
    onNext: (name: String, email: String, password: String) -> Unit = { _, _, _ -> },
    onSignIn: () -> Unit = {},
) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    val isNameValid = name.trim().length >= 2
    val isEmailValid =
        android.util.Patterns.EMAIL_ADDRESS
            .matcher(email)
            .matches()
    val isPasswordValid = password.length >= 8
    val doPasswordsMatch = password == confirmPassword && confirmPassword.isNotEmpty()

    val canProceed = isNameValid && isEmailValid && isPasswordValid && doPasswordsMatch

    ElevatedCard(
        modifier =
            modifier
                .fillMaxSize()
                .padding(16.dp),
        shape = RoundedCornerShape(12.dp),
    ) {
        Column(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp, alignment = Alignment.Top),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                "Create your account",
                style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.SemiBold),
                modifier = Modifier.fillMaxWidth(),
            )

            Text(
                "Enter your details to set up your admin account.",
                style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.onSurfaceVariant),
                modifier = Modifier.fillMaxWidth(),
            )

            Spacer(modifier = Modifier.height(4.dp))

            Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Text("Full name", style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant))
                SharedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = "Full name",
                    placeHolderText = "Your full name",
                )
                if (name.isNotEmpty() && !isNameValid) {
                    Text(
                        "Please enter at least 2 characters.",
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall,
                    )
                }

                Text("Email", style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant))
                SharedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = "Email",
                    placeHolderText = "name@company.com",
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                )
                if (email.isNotEmpty() && !isEmailValid) {
                    Text(
                        "Enter a valid email address.",
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall,
                    )
                }

                Text("Password", style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant))
                SharedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = "Password",
                    placeHolderText = "At least 8 characters",
                    isPassword = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                )
                if (password.isNotEmpty() && !isPasswordValid) {
                    Text(
                        "Password should be at least 8 characters.",
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall,
                    )
                }

                Text(
                    "Confirm Password",
                    style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant),
                )
                SharedTextField(
                    value = confirmPassword,
                    onValueChange = { confirmPassword = it },
                    label = "Confirm password",
                    isPassword = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                )
                if (confirmPassword.isNotEmpty() && !doPasswordsMatch) {
                    Text("Passwords do not match.", color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodySmall)
                }
            }

            Spacer(modifier = Modifier.size(4.dp))

            Button(
                onClick = { if (canProceed) onNext(name.trim(), email.trim(), password) },
                modifier = Modifier.fillMaxWidth(),
                enabled = canProceed,
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
            ) {
                Text("Create account", color = MaterialTheme.colorScheme.onPrimary)
            }

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                Text("Already have an account? ")
                TextButton(onClick = onSignIn) {
                    Text("Sign in", color = MaterialTheme.colorScheme.primary)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CreateAccountPreview() {
    CreateAccountScreen()
}
