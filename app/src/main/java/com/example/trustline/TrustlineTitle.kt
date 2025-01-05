package com.example.trustline

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun TrustlineTitle(
    textColor: Color = MaterialTheme.colorScheme.primaryContainer,
    modifier: Modifier = Modifier
) {
    Text(
        modifier = modifier,
        color = textColor,
        style = MaterialTheme.typography.displayLarge,
        text = "TrustLine"
    )
}