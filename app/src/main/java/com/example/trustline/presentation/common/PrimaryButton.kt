package com.example.trustline.presentation.common

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import com.example.trustline.R

@Composable
fun PrimaryButton(
    title: String,
    enabled: Boolean = true,
    loading: Boolean = false,
    onButtonClicked: () -> Unit
) {
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .height(dimensionResource(id = R.dimen.height_fifty_two)),
        colors = ButtonDefaults.buttonColors(
            containerColor = colorResource(id = R.color.primary),
            contentColor = colorResource(id = R.color.white_2),
            disabledContainerColor = colorResource(id = R.color.primary_accent),
            disabledContentColor = colorResource(id = R.color.white)
        ),
        enabled = enabled,
        shape = MaterialTheme.shapes.small,
        onClick = onButtonClicked
    ) {
        if (loading) CircularProgressIndicator(
            color = colorResource(id = R.color.deep_grey),
            trackColor = MaterialTheme.colorScheme.surfaceVariant
        ) else Text(title)
    }

}
