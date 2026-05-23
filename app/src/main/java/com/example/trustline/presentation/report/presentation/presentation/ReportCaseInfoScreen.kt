package com.example.trustline.presentation.report.presentation.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.trustline.R
import com.example.trustline.presentation.common.PrimaryButton
import com.example.trustline.ui.theme.TrustlineTheme

@Composable
fun ReportCaseInfoScreen(
    modifier: Modifier = Modifier,
    onBackClicked: () -> Unit = {},
    onContinueClicked: () -> Unit = {},
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            IconButton(
                onClick = onBackClicked,
                modifier = Modifier
                    .padding(dimensionResource(id = R.dimen.padding_small))
                    .windowInsetsPadding(WindowInsets.statusBars)
            ) {
                Box(
                    modifier = Modifier
                        .size(24.dp)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back"
                    )
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(horizontal = dimensionResource(id = R.dimen.padding_large))
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.height_thirty_two)))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.speech_cover),
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Transparent),
                    contentDescription = stringResource(id = R.string.report_case_icon)
                )
            }

            Spacer(modifier = Modifier.height(30.dp))
//title
            Text(
                text = stringResource(id = R.string.report_a_case),
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.height_twelve)))

            Text(
                text = stringResource(id = R.string.report_case_description),
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center,
                lineHeight = 24.sp
            )

            Spacer(modifier = Modifier.weight(1f))

            PrimaryButton(
                title = stringResource(id = R.string.continue_text),
                onButtonClicked = onContinueClicked
            )

            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.height_forty)))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ReportCaseInfoScreenPreview() {
    TrustlineTheme {
        ReportCaseInfoScreen()
    }
}
