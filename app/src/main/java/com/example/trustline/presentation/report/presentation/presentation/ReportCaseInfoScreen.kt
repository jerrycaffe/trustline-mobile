package com.example.trustline.presentation.report.presentation.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.trustline.R
import com.example.trustline.presentation.common.ShowCaseScreen
import com.example.trustline.ui.theme.TrustlineTheme

@Composable
fun ReportCaseInfoScreen(
    modifier: Modifier = Modifier,
    onBackClicked: () -> Unit = {},
    onContinueClicked: () -> Unit = {},
) {
    ShowCaseScreen(
        screenDescription = R.string.report_case_description,
        screenName = R.string.report_case_icon,
        title = R.string.report_a_case,
        screenImage = R.drawable.speech_cover,
        onBackClicked = onBackClicked,
        onContinueClicked = onContinueClicked
    )
}

@Preview(showBackground = true)
@Composable
fun ReportCaseInfoScreenPreview() {
    TrustlineTheme {
        ReportCaseInfoScreen()
    }
}
