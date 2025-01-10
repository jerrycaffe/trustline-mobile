package com.example.trustline.presentation.auth.login.presentation

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.trustline.R
import com.example.trustline.presentation.common.PrimaryButton
import com.example.trustline.presentation.common.TrustlineTitle


@Composable
fun LoginScreen(modifier: Modifier = Modifier) {
    val viewModel = viewModel<LoginViewModel>()
    val state = viewModel.state
    val context = LocalContext.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(dimensionResource(id = R.dimen.padding_medium))

    ) {
        //if validation is successful
        LaunchedEffect(key1 = context) {
            viewModel.validationEvents.collect { event ->
                when (event) {
                    is LoginViewModel.ValidationEvent.Success -> {
                        Toast.makeText(context, "Login successful", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = dimensionResource(R.dimen.padding_large)),
            horizontalArrangement = Arrangement.Center
        ) {
            TrustlineTitle()
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .align(Alignment.Center),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                style = MaterialTheme.typography.titleMedium,
                text = stringResource(id = R.string.login_title)
            )
            Text(
                text = stringResource(id = R.string.login_description)
            )
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.height_forty)))
            Column(
                verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.space_ten)),
            ) {
                InputTextBox(value = state.email,
                    isError = state.emailError != null,
                    placeHolder = stringResource(id = R.string.email),
                    keyboardType = KeyboardType.Email,
                    onValueChanged = {
                        viewModel.onEvent(LoginFormEvent.EmailChanged(it))

                    })
                //Display error
                if (state.emailError != null) displayErrorMessage(state.emailError)

                InputTextBox(
                    value = state.password,
                    isError = state.passwordError != null,
                    keyboardType = KeyboardType.Password,
                    placeHolder = stringResource(id = R.string.password),
                    onValueChanged = { viewModel.onEvent(LoginFormEvent.PasswordChanged(it)) },
                    isPasswordField = true
                )
                if (state.passwordError != null) displayErrorMessage(state.passwordError)
                PrimaryButton(title = stringResource(id = R.string.login),
                    enabled = state.isAllFieldValid,
                    onButtonClicked = {
                        viewModel.onEvent((LoginFormEvent.Submit))
                    })
            }
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.height_twenty_four)))
            Column(
                verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.height_twenty_four))
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween

                ) {
                    Divider(
                        modifier = Modifier
                            .weight(1f)
                            .height(1.dp)
                    )
                    Text(
                        color = colorResource(id = R.color.deep_grey),
                        modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.padding_small)),
                        text = stringResource(id = R.string.continue_with_socials)
                    )
                    Divider(
                        modifier = Modifier
                            .weight(1f)
                            .height(1.dp)
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.google_icon),
                        contentDescription = stringResource(id = R.string.app_logo)
                    )
                }
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
                        append("Terms")
                    })
                    Text(buildAnnotatedString {
                        append("of Service")
                        withStyle(style = SpanStyle(color = colorResource(id = R.color.deep_grey))) {
                            append(
                                " and "
                            )
                        }
                        append("Privacy Policy")
                    })
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(bottom = dimensionResource(id = R.dimen.padding_extra_large)),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(color = colorResource(id = R.color.deep_grey), text = "Already have an account? ")
            Text(text = "Sign up")
        }

    }


}

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

@Composable
fun displayErrorMessage(errorMessage: String) {
    Text(text = errorMessage, color = MaterialTheme.colorScheme.error)
}