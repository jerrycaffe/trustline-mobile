package com.example.trustline.presentation.trackCase.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.trustline.R
import com.example.trustline.presentation.common.ShowCaseScreen
import com.example.trustline.ui.theme.TrustlineTheme

@Composable
fun TrackCaseInfoScreen(
    modifier: Modifier = Modifier,
    onBackClicked: () -> Unit = {},
    onContinueClicked: () -> Unit = {},
) {
    ShowCaseScreen(
        screenDescription = R.string.track_case_description,
        screenName = R.string.track_a_case,
        screenImage = R.drawable.location_icon,
        title = R.string.track_a_case,
        onBackClicked = onBackClicked,
        onContinueClicked = onContinueClicked
    )
}

@Preview(showBackground = true)
@Composable
fun TrackCaseInfoScreenPreview() {
    TrustlineTheme {
        TrackCaseInfoScreen()
    }
}
