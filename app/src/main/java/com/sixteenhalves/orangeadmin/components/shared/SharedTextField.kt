package com.sixteenhalves.orangeadmin.components.shared

import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable

@Composable
fun SharedTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeHolderText: String? = null,
) {
    TextField(
        value = value,
        onValueChange = { text: String -> onValueChange(text) },
    )
}
