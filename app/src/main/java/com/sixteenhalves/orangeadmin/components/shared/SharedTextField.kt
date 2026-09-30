package com.sixteenhalves.orangeadmin.components.shared

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import com.sixteenhalves.orangeadmin.R

@Composable
fun SharedTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String? = null,
    placeHolderText: String? = null,
    isPassword: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = true,
) {
    val passwordVisible = remember { mutableStateOf(false) }
    val visualTransformation: VisualTransformation =
        if (isPassword && !passwordVisible.value) PasswordVisualTransformation() else VisualTransformation.None

    TextField(
        value = value,
        onValueChange = { text: String -> onValueChange(text) },
        modifier = Modifier.fillMaxWidth(),
        label = label?.let { { Text(it) } },
        placeholder = placeHolderText?.let { { Text(it) } },
        singleLine = singleLine,
        visualTransformation = visualTransformation,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        trailingIcon =
            if (isPassword) {
                {
                    IconButton(onClick = { passwordVisible.value = !passwordVisible.value }) {
                        val icon = if (passwordVisible.value) R.drawable.baseline_visibility_off_24 else R.drawable.baseline_visibility_24
                        Icon(
                            painter = painterResource(icon),
                            contentDescription = if (passwordVisible.value) "Hide password" else "Show password",
                        )
                    }
                }
            } else {
                null
            },
    )
}
