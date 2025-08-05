package com.example.trustline.presentation.common

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import com.example.trustline.R

@Composable
fun TermsAndConditionSection() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(buildAnnotatedString {
            withStyle(style = SpanStyle(colorResource(id = R.color.deep_grey))) {
                append(
                    "By clicking continue, you agree to our "
                )
            }
            withStyle(style = MaterialTheme.typography.titleSmall.toSpanStyle()) {
                append("Terms")
            }

        })
        Text(buildAnnotatedString {
            append("of Service")
            withStyle(style = SpanStyle(color = colorResource(id = R.color.deep_grey))) {
                append(
                    " and "
                )
            }
            withStyle(style = MaterialTheme.typography.titleSmall.toSpanStyle()) {
                append("Privacy Policy")
            }

        })
    }
}