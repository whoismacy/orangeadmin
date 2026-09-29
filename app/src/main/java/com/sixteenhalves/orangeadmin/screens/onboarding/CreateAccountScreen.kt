package com.sixteenhalves.orangeadmin.screens.onboarding

import android.util.Patterns
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
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
import androidx.compose.ui.text.style.TextAlign
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
    val state = rememberCreateAccountState()
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 24.dp)
            .verticalScroll(scrollState)
            .imePadding(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.height(48.dp))
        
        HeaderSection()

        Spacer(modifier = Modifier.height(32.dp))

        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            InputFieldGroup(
                label = "Full Name",
                value = state.name,
                onValueChange = { state.name = it },
                placeHolderText = "Your full name",
                errorMessage = state.nameError
            )

            InputFieldGroup(
                label = "Email Address",
                value = state.email,
                onValueChange = { state.email = it },
                placeHolderText = "name@company.com",
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                errorMessage = state.emailError
            )

            InputFieldGroup(
                label = "Password",
                value = state.password,
                onValueChange = { state.password = it },
                placeHolderText = "At least 8 characters",
                isPassword = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                errorMessage = state.passwordError
            )

            InputFieldGroup(
                label = "Confirm Password",
                value = state.confirmPassword,
                onValueChange = { state.confirmPassword = it },
                placeHolderText = "Re-enter password",
                isPassword = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                errorMessage = state.confirmPasswordError
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        ActionSection(
            canProceed = state.canProceed,
            onNext = {
                if (state.canProceed) {
                    authViewModel.updateEmail(state.email.trim())
                    authViewModel.updatePassword(state.password)
                    authViewModel.updateName(state.name.trim())
                    onNext()
                }
            },
            onSignIn = onSignIn
        )
        
        Spacer(modifier = Modifier.height(48.dp))
    }
}

class CreateAccountState {
    var name by mutableStateOf("")
    var email by mutableStateOf("")
    var password by mutableStateOf("")
    var confirmPassword by mutableStateOf("")

    val isNameValid: Boolean
        get() = name.trim().length >= 4

    val isEmailValid: Boolean
        get() = Patterns.EMAIL_ADDRESS.matcher(email).matches()

    val isPasswordValid: Boolean
        get() = password.length >= 8

    val doPasswordsMatch: Boolean
        get() = password == confirmPassword && confirmPassword.isNotEmpty()

    val canProceed: Boolean
        get() = isNameValid && isEmailValid && isPasswordValid && doPasswordsMatch

    val nameError: String?
        get() = if (name.isNotEmpty() && !isNameValid) "Please enter at least 4 characters." else null

    val emailError: String?
        get() = if (email.isNotEmpty() && !isEmailValid) "Enter a valid email address." else null

    val passwordError: String?
        get() = if (password.isNotEmpty() && !isPasswordValid) "Password should be at least 8 characters." else null

    val confirmPasswordError: String?
        get() = if (confirmPassword.isNotEmpty() && !doPasswordsMatch) "Passwords do not match." else null
}

@Composable
fun rememberCreateAccountState() = remember { CreateAccountState() }

@Composable
private fun HeaderSection() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Create your account",
            style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onBackground
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Enter your details to set up an admin account and manage your store.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )
    }
}


@Composable
private fun InputFieldGroup(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    placeHolderText: String,
    isPassword: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    errorMessage: String? = null
) {
    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.SemiBold),
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        SharedTextField(
            value = value,
            onValueChange = onValueChange,
            placeHolderText = placeHolderText,
            isPassword = isPassword,
            keyboardOptions = keyboardOptions
        )
        if (errorMessage != null) {
            Text(
                text = errorMessage,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
            )
        }
    }
}

@Composable
private fun ActionSection(
    canProceed: Boolean,
    onNext: () -> Unit,
    onSignIn: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Button(
            onClick = onNext,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(12.dp),
            enabled = canProceed,
        ) {
            Text(
                text = "Create account",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold)
            )
        }

        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = "Already have an account?",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            TextButton(onClick = onSignIn) {
                Text(
                    text = "Sign in",
                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}
