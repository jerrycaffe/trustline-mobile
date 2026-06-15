package com.example.trustline.presentation.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.trustline.R

@Composable
fun ShowCaseScreen(
    modifier: Modifier = Modifier,
    screenImage: Int,
    title: Int? = null,
    screenName: Int,
    screenDescription: Int,
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

            ShowCaseScreenDescriptionImg(screenImage, screenName)

            Spacer(modifier = Modifier.height(30.dp))
//title
            if (title != null) ShowCaseScreenTitle(title)

            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.height_twelve)))

            ShowCaseScreenDescription(screenDescription)

            Spacer(modifier = Modifier.weight(1f))

            PrimaryButton(
                title = stringResource(id = R.string.continue_text),
                onButtonClicked = onContinueClicked
            )

            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.height_forty)))
        }
    }
}