package com.example.trustline.presentation.common

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp

@Composable
fun ShowCaseScreenDescription(description: Int){
    Text(
        text = stringResource(id = description),
        fontSize = 16.sp,
        textAlign = TextAlign.Center,
        lineHeight = 28.sp
    )
}