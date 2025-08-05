package com.example.trustline.presentation.common

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import com.example.trustline.R

@Composable
fun TrustlineTitle(
    textColor: Color = colorResource(id = R.color.primary),
    modifier: Modifier = Modifier
) {
    Text(
        modifier = modifier,
        color = textColor,
        style = MaterialTheme.typography.displayLarge,
        text = "TrustLine"
    )
}