package com.sixteenhalves.orangeadmin.screens.onboarding

import android.util.Patterns
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
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
import androidx.compose.ui.unit.dp
import com.sixteenhalves.orangeadmin.components.shared.SharedTextField
import com.sixteenhalves.orangeadmin.viewmodels.AuthViewModel

@Composable
fun CreateAccountScreen(
    authViewModel: AuthViewModel,
    onNext: () -> Unit,
    modifier: Modifier = Modifier,
    onSignIn: () -> Unit = {},
) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    val isNameValid = name.trim().length >= 4
    val isEmailValid =
        Patterns.EMAIL_ADDRESS
            .matcher(email)
            .matches()
    val isPasswordValid = password.length >= 8
    val doPasswordsMatch = password == confirmPassword && confirmPassword.isNotEmpty()

    val canProceed = isNameValid && isEmailValid && isPasswordValid && doPasswordsMatch

    Box(
        modifier = modifier.fillMaxSize().padding(16.dp),
        contentAlignment = Alignment.Center,
    ) {
        OutlinedCard(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .heightIn(),
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
                    "Enter your details to set up an admin account.",
                    style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.onSurfaceVariant),
                    modifier = Modifier.fillMaxWidth(),
                )

                Spacer(modifier = Modifier.height(4.dp))

                Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Text("Full name", style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant))
                    SharedTextField(
                        value = name,
                        onValueChange = { name = it },
                        placeHolderText = "Your full name",
                    )
                    if (name.isNotEmpty() && !isNameValid) {
                        Text(
                            "Please enter at least 4 characters.",
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodySmall,
                        )
                    }

                    Text("Email", style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant))
                    SharedTextField(
                        value = email,
                        onValueChange = { email = it },
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
                        isPassword = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    )
                    if (confirmPassword.isNotEmpty() && !doPasswordsMatch) {
                        Text("Passwords do not match.", color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodySmall)
                    }
                }

                Spacer(Modifier.height(8.dp))

                Button(
                    onClick = {
                        if (canProceed) {
                            authViewModel.updateEmail(email)
                            authViewModel.updatePassword(password)
                            authViewModel.updateName(name)
                            onNext()
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = canProceed,
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                ) {
                    Text("Create account", color = MaterialTheme.colorScheme.onPrimary)
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text("Already have an account?")
                    TextButton(onClick = onSignIn) {
                        Text("Sign in", color = MaterialTheme.colorScheme.primary)
                    }
                }
            }
        }
    }
}
