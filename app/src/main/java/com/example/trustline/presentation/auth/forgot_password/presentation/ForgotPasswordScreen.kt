package com.example.trustline.presentation.auth.forgot_password.presentation

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.trustline.R
import com.example.trustline.presentation.common.ErrorMessageComponent
import com.example.trustline.presentation.common.InputTextBox
import com.example.trustline.presentation.common.PrimaryButton


@Composable
fun ForgotPasswordScreen(navController: NavHostController, modifier: Modifier = Modifier) {
    val viewModel = viewModel<ForgotPasswordViewModel>()
    val state = viewModel.state
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current

    Box(modifier = Modifier
        .fillMaxSize()
        .pointerInput(Unit) {
            detectTapGestures { focusManager.clearFocus() }
        }
        .padding(dimensionResource(id = R.dimen.padding_medium))

    ) {
        //if validation is successful
        LaunchedEffect(key1 = context) {
            viewModel.validationEvents.collect { event ->
                when (event) {
                    is ForgotPasswordViewModel.ValidationEvent.Success -> {
                        Toast.makeText(context, "Forgot password successful", Toast.LENGTH_LONG)
                            .show()
                    }
                }
            }
        }



        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .align(Alignment.Center),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.amico),
                contentDescription = stringResource(
                    id = R.string.forgot_password_image
                )
            )
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.height_forty)))
            Text(
                style = MaterialTheme.typography.titleMedium,
                text = stringResource(id = R.string.forgot_password)
            )

            Text(
                modifier = Modifier
                    .fillMaxWidth(),
                textAlign = TextAlign.Center,
                text = stringResource(id = R.string.forgot_password_description)
            )
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.height_twenty)))

            InputTextBox(value = state.email,
                isError = state.emailError != null,
                placeHolder = stringResource(id = R.string.email),
                keyboardType = KeyboardType.Email,
                onValueChanged = {
                    viewModel.onEvent(ForgotPasswordFormEvent.EmailChanged(it))

                })
            //Display error
            if (state.emailError != null) ErrorMessageComponent(state.emailError)

            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.height_forty)))

            PrimaryButton(title = stringResource(id = R.string.submit),
                enabled = state.isFieldValid,
                onButtonClicked = {
                    viewModel.onEvent((ForgotPasswordFormEvent.Submit))
                })


        }


    }


}




