package com.example.trustline.presentation.seekHelp.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.trustline.R
import com.example.trustline.presentation.common.ShowCaseScreen
import com.example.trustline.ui.theme.TrustlineTheme

@Composable
fun SeekHelpCaseInfoScreen(
    modifier: Modifier = Modifier,
    onBackClicked: () -> Unit = {},
    onContinueClicked: () -> Unit = {},
) {
    ShowCaseScreen(
        screenDescription = R.string.seek_help_description,
        screenName = R.string.seek_help,
        screenImage = R.drawable.seek_help,
        title = R.string.seek_help,
        onBackClicked = onBackClicked,
        onContinueClicked = onContinueClicked
    )
}

@Preview(showBackground = true)
@Composable
fun SeekHelpCaseInfoScreenPreview() {
    TrustlineTheme {
        SeekHelpCaseInfoScreen()
    }
}
