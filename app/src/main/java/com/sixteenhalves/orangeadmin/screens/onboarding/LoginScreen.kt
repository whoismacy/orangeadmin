package com.sixteenhalves.orangeadmin.screens.onboarding

import android.util.Patterns
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.size
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

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    onLogin: () -> Unit = {},
    onForgot: () -> Unit = {},
    onSignUp: () -> Unit = {},
) {
    var emailAddress by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val emailIsValid = Patterns.EMAIL_ADDRESS.matcher(emailAddress).matches()
    val passwordValid = password.length >= 8
    val canProceed = emailIsValid && passwordValid

    Box(
        modifier =
            modifier
                .fillMaxSize()
                .padding(16.dp)
                .background(MaterialTheme.colorScheme.background),
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
                        .padding(24.dp)
                        .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(16.dp, alignment = Alignment.Top),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.Start,
                ) {
                    Text(
                        "Welcome back",
                        style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.SemiBold),
                    )
                    Text(
                        "Sign in to continue to your admin dashboard",
                        style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.onSurfaceVariant),
                        modifier = Modifier.padding(top = 6.dp),
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))

                Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Text("Email", style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant))
                    SharedTextField(
                        value = emailAddress,
                        onValueChange = { emailAddress = it },
                        placeHolderText = "name@company.com",
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    )
                    if (emailAddress.isNotEmpty() && !emailIsValid) {
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
                        placeHolderText = "Enter your password",
                        isPassword = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    )
                    if (password.isNotEmpty() && !passwordValid) {
                        Text(
                            "Password should be at least 8 characters.",
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodySmall,
                        )
                    }

                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                        TextButton(onClick = onForgot) {
                            Text("Forgot Password?", color = MaterialTheme.colorScheme.primary)
                        }
                    }
                }

                Button(
                    onClick = onLogin,
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                    enabled = canProceed,
                ) {
                    Text("Login", color = MaterialTheme.colorScheme.onPrimary)
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text("Don't have an account?")
                    TextButton(onClick = onSignUp) {
                        Text("Sign up", color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.SemiBold)
                    }
                }
            }
        }
    }
}
