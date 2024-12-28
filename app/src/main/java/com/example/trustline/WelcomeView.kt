package com.example.trustline

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun WelcomeView(modifier: Modifier = Modifier) {
    Column(
        Modifier
            .padding(16.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TrustLineLogoImg()
        TrustlineTitle()
        Column(
            Modifier
                .padding(top = 94.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            PrimaryButton(title = "Report a case", onButtonClicked = {/*TODO add navigation to report case*/ })
            PrimaryOutlinedButton("Seek help", onButtonClicked = {/*TODO navigate to seek help*/ })

        }

    }
}