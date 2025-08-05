package com.example.trustline.presentation.auth.reset_password.presentation

import android.util.Log
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.trustline.R
import com.example.trustline.presentation.common.ErrorMessageComponent
import com.example.trustline.presentation.common.InputTextBox
import com.example.trustline.presentation.common.PrimaryButton


@Composable
fun ResetPasswordScreen(
    navController: NavHostController,
    innerPadding: Dp = 16.dp,
    modifier: Modifier = Modifier
) {
    val viewModel = viewModel<ResetPasswordViewModel>()
    val state = viewModel.resetPasswordState.collectAsState()
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
                    is ResetPasswordViewModel.ValidationEvent.Success -> {
                        Toast.makeText(context, "Reset password successful", Toast.LENGTH_LONG)
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
                painter = painterResource(id = R.drawable.rafiki),
                contentDescription = stringResource(
                    id = R.string.reset_password_image
                )
            )
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.height_fifty_two)))
            Text(
                style = MaterialTheme.typography.titleMedium,
                text = stringResource(id = R.string.reset_password)
            )

            Text(
                modifier = Modifier
                    .fillMaxWidth(),
                textAlign = TextAlign.Center,
                text = stringResource(id = R.string.reset_password_description)
            )
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.height_thirty_two)))

            InputTextBox(value = state.value.password,
                isError = state.value.passwordError != null,
                placeHolder = stringResource(id = R.string.password),
                keyboardType = KeyboardType.Password,
                isPasswordField = true,
                onValueChanged = {
                    viewModel.onEvent(ResetPasswordFormEvent.PasswordChanged(it))

                })
            //Display error

            if (state.value.passwordError != null) Row(modifier = Modifier.align(Alignment.Start)) {
                ErrorMessageComponent(state.value.passwordError!!)
            }

            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.height_sixteen)))

            InputTextBox(value = state.value.confirmPassword,
                isError = state.value.confirmPasswordError != null,
                placeHolder = stringResource(id = R.string.confirm_password),
                keyboardType = KeyboardType.Password,
                isPasswordField = true,
                onValueChanged = {
                    viewModel.onEvent(ResetPasswordFormEvent.ConfirmPasswordChanged(it))

                })
            //Display error

            Log.d("Password check", state.value.isAllFieldValid.toString())
            state.value.passwordError?.let { Log.d("Password check", it) }
            state.value.confirmPasswordError?.let { Log.d("Password check", it) }


            if (state.value.confirmPasswordError != null) {
                Row(modifier = Modifier.align(Alignment.Start)) {
                    ErrorMessageComponent(state.value.confirmPasswordError!!)
                }
            }
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.height_sixteen)))
            PrimaryButton(title = stringResource(id = R.string.reset_password),
                enabled = viewModel.isPasswordMatch(),
                onButtonClicked = {
                    viewModel.onEvent((ResetPasswordFormEvent.Submit))
                })


        }


    }


}




