package com.example.trustline

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp

@Composable
fun PrimaryButton(title: String, enabled: Boolean = true, onButtonClicked: () -> Unit) {
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .height(52.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = colorResource(id = R.color.primary),
            contentColor = colorResource(id = R.color.white),
            disabledContainerColor = colorResource(id = R.color.primary_accent),
            disabledContentColor = colorResource(id = R.color.white)
        ),
        enabled = enabled,
        shape = RoundedCornerShape(8.dp),
        onClick = onButtonClicked
    ) {
        Text(title)
    }
}