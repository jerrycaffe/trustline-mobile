package com.example.trustline.presentation.auth.register.presentation

import android.widget.Toast
import androidx.compose.foundation.Image
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
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.trustline.R
import com.example.trustline.presentation.common.ErrorMessageComponent
import com.example.trustline.presentation.common.InputTextBox
import com.example.trustline.presentation.common.PrimaryButton
import com.example.trustline.presentation.common.TrustlineTitle


@Composable
fun SignupScreen(modifier: Modifier = Modifier) {
    val viewModel = viewModel<SignupViewModel>()
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
                    is SignupViewModel.ValidationEvent.Success -> {
                        Toast.makeText(context, "Registration successful", Toast.LENGTH_LONG).show()
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
                text = stringResource(id = R.string.signup_title)
            )
            Text(
                text = stringResource(id = R.string.signup_description)
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
                        viewModel.onEvent(RegistrationFormEvent.EmailChanged(it))

                    })
                //Display error
                if (state.emailError != null) ErrorMessageComponent(state.emailError)

                InputTextBox(
                    value = state.phoneNumber,
                    placeHolder = stringResource(id = R.string.phone_number),
                    isError = state.phoneNumberError != null,
                    keyboardType = KeyboardType.Phone,
                    onValueChanged = {
                        viewModel.onEvent(RegistrationFormEvent.PhoneNumberChanged(it))
                    })
                if (state.phoneNumberError != null) ErrorMessageComponent(state.phoneNumberError)
                InputTextBox(
                    value = state.password,
                    isError = state.passwordError != null,
                    keyboardType = KeyboardType.Password,
                    placeHolder = stringResource(id = R.string.password),
                    onValueChanged = { viewModel.onEvent(RegistrationFormEvent.PasswordChanged(it)) },
                    isPasswordField = true
                )
                if (state.passwordError != null) ErrorMessageComponent(state.passwordError)
                PrimaryButton(title = stringResource(id = R.string.sing_up),
                    enabled = state.isAllFieldValid,
                    onButtonClicked = {
                        viewModel.onEvent((RegistrationFormEvent.Submit))
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
            Text(text = "Login")
        }

    }


}



