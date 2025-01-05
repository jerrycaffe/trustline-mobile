package com.example.trustline

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource

@Composable
fun WelcomeView(modifier: Modifier = Modifier) {
    Column(
        Modifier
            .padding(dimensionResource(id = R.dimen.padding_medium))
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TrustLineLogoImg()
        TrustlineTitle()
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.height_ninety_four)))
        Column(
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.space_sixteen))
        ) {
            PrimaryButton(
                title = "Report a case",
                onButtonClicked = {/*TODO add navigation to report case*/ })
            PrimaryOutlinedButton("Seek help", onButtonClicked = {/*TODO navigate to seek help*/ })

        }

    }
}