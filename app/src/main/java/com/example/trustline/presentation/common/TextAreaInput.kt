package com.example.trustline.presentation.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun TextAreaInput(
    value: String,
    onValueChanged: (String) -> Unit,
    placeHolder: String = "Enter description"
) {
    var text by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(140.dp)
            .background(
                color = Color.LightGray.copy(alpha = 0.2f),
                shape = RoundedCornerShape(12.dp)
            )
            .padding(16.dp)
    ) {
        if (value.isEmpty()) {
            Text(
                text = placeHolder,
                color = Color.Gray
            )
        }

        BasicTextField(
            value = value,
            onValueChange = onValueChanged,
            modifier = Modifier.fillMaxWidth(),
            maxLines = 8
        )
    }
}