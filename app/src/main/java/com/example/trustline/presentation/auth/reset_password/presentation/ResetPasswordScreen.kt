package com.example.trustline.presentation.auth.reset_password.presentation

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectTapGestures
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
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.trustline.R
import com.example.trustline.presentation.common.ErrorMessageComponent
import com.example.trustline.presentation.common.InputTextBox
import com.example.trustline.presentation.common.PrimaryButton
import com.example.trustline.presentation.common.TermsAndConditionSection


@Composable
fun ResetPasswordScreen(
    navController: NavHostController,
    innerPadding: Dp = 16.dp,
    modifier: Modifier = Modifier
) {
    val viewModel = viewModel<ResetPasswordViewModel>()
    val state = viewModel.state
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current

    Box(modifier = Modifier
        .fillMaxSize()
        .pointerInput(Unit) {
            detectTapGestures { focusManager.clearFocus() }
        }
        .padding(innerPadding)

    ) {
        //if validation is successful
        LaunchedEffect(key1 = context) {
            viewModel.validationEvents.collect { event ->
                if (event is ResetPasswordViewModel.ValidationEvent.Success) {
                    Toast.makeText(context, "Login successful", Toast.LENGTH_LONG).show()
                }
            }
        }


        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = dimensionResource(R.dimen.padding_one_hundred_and_five)),
            horizontalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.amico),
                contentDescription = stringResource(
                    id = R.string.forgot_password_image
                )
            )
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


                InputTextBox(
                    value = state.password,
                    isError = state.passwordError != null,
                    keyboardType = KeyboardType.Password,
                    placeHolder = stringResource(id = R.string.password),
                    onValueChanged = { viewModel.onEvent(ResetPasswordFormEvent.PasswordChanged(it)) },
                    isPasswordField = true
                )
                if (state.passwordError != null) ErrorMessageComponent(state.passwordError)
                PrimaryButton(title = stringResource(id = R.string.login),
                    enabled = state.isAllFieldValid,
                    onButtonClicked = {
                        viewModel.onEvent((ResetPasswordFormEvent.Submit))
                    })
            }
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.height_twelve)))
            Text(
                modifier = Modifier.align(Alignment.End),
                color = colorResource(id = R.color.primary),
                text = "Forgot Password?"
            )
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.height_forty)))
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
                TermsAndConditionSection()
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
            Text(

                style = MaterialTheme.typography.titleSmall, text = "Sign up",
                textDecoration = TextDecoration.Underline
            )
        }

    }


}




