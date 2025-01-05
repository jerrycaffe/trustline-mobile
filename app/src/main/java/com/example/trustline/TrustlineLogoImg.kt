package com.example.trustline

import androidx.compose.foundation.Image
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource

@Composable
fun TrustLineLogoImg(color: Color = MaterialTheme.colorScheme.primaryContainer) {
    Image(
        painter = painterResource(id = R.drawable.trustline_logo),
        colorFilter = ColorFilter.tint(color),
        contentDescription = "Trustline logo"
    )
}