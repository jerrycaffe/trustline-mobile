package com.example.trustline.presentation.splash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.trustline.presentation.common.TrustLineLogoImg
import com.example.trustline.presentation.common.TrustlineTitle

@Composable
fun SplashScreen(modifier: Modifier = Modifier) {
    Column(
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primaryContainer),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        TrustLineLogoImg(MaterialTheme.colorScheme.onPrimaryContainer)
        TrustlineTitle(MaterialTheme.colorScheme.onPrimaryContainer)
    }
}