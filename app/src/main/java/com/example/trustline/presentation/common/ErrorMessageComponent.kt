package com.example.trustline.presentation.common

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun ErrorMessageComponent(errorMessage: String) {
    Text(text = errorMessage, color = MaterialTheme.colorScheme.error)
}