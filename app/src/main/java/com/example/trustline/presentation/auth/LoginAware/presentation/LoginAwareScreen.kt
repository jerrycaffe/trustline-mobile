package com.example.trustline.presentation.auth.LoginAware.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.trustline.R
import com.example.trustline.presentation.common.ShowCaseScreen
import com.example.trustline.ui.theme.TrustlineTheme

@Composable
fun LoginAwareScreen(
    modifier: Modifier = Modifier,
    onBackClicked: () -> Unit = {},
    onContinueClicked: () -> Unit = {},
) {
    ShowCaseScreen(
        screenDescription = R.string.login_aware_descrption,
        screenName = R.string.login_aware,
        screenImage = R.drawable.aware_icon,
        onBackClicked = onBackClicked,
        onContinueClicked = onContinueClicked
    )
}

@Preview(showBackground = true)
@Composable
fun LoginAwareScreenPreview() {
    TrustlineTheme {
        LoginAwareScreen()
    }
}
