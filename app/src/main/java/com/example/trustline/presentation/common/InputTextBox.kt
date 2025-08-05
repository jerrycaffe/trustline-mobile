package com.example.trustline.presentation.common

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.trustline.R

@Composable
fun InputTextBox(
    value: String,
    placeHolder: String,
    isPasswordField: Boolean = false,
    isError: Boolean = false,
    keyboardType: KeyboardType = KeyboardType.Text,

    onValueChanged: (String) -> Unit
) {
    var isFocused by remember { mutableStateOf(false) }
    var showPassword by remember { mutableStateOf(false) }
    val borderColor = if (isError) MaterialTheme.colorScheme.error else Color.LightGray
    BasicTextField(modifier = Modifier.onFocusChanged { focusedState ->
        isFocused = focusedState.isFocused
    },
        visualTransformation = if (isPasswordField && !showPassword) PasswordVisualTransformation() else VisualTransformation.None,
        singleLine = true,
        value = value,
        onValueChange = onValueChanged,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        decorationBox = { innerTextField ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(
                        1.dp, borderColor, MaterialTheme.shapes.small
                    )
                    .padding(horizontal = dimensionResource(id = R.dimen.padding_medium))
                    .height(dimensionResource(id = R.dimen.height_semi_tall)),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Absolute.SpaceBetween
            ) {
                if (value.isEmpty() && !isFocused) {
                    Text(
                        color = colorResource(id = R.color.deep_grey), text = placeHolder
                    )
                }
                innerTextField()
                if (isPasswordField) {
                    IconButton(onClick = { showPassword = !showPassword }

                    ) {
                        Icon(
                            tint = colorResource(id = R.color.deep_grey),
                            painter = if (showPassword) painterResource(R.drawable.eye_opened) else painterResource(
                                R.drawable.eye_closed
                            ),
                            contentDescription = if (showPassword) stringResource(id = R.string.hide_password) else stringResource(
                                id = R.string.show_password
                            )
                        )
                    }
                }

            }
        })
}