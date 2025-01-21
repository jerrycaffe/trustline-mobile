package com.example.trustline.presentation.auth.otp.presentation

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.trustline.R
import com.example.trustline.presentation.auth.forgot_password.presentation.ForgotPasswordFormEvent
import com.example.trustline.presentation.auth.forgot_password.presentation.OtpVerificationScreenViewModel
import com.example.trustline.presentation.common.PrimaryButton

@Composable
fun OtpVerificationScreen(navController: NavHostController, modifier: Modifier = Modifier) {
    val viewModel = viewModel<OtpVerificationScreenViewModel>()
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
//        LaunchedEffect(key1 = context) {
//            viewModel.validationEvents.collect { event ->
//                when (event) {
//                    is ForgotPasswordViewModel.ValidationEvent.Success -> {
//                        Toast.makeText(context, "Forgot password successful", Toast.LENGTH_LONG)
//                            .show()
//                    }
//                }
//            }
//        }


        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .align(Alignment.Center),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.otp_verification),
                contentDescription = stringResource(
                    id = R.string.otp_verification
                )
            )
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.height_fifty)))
            Text(
                style = MaterialTheme.typography.titleMedium,
                text = stringResource(id = R.string.otp_verification)
            )

            Text(
                modifier = Modifier
                    .fillMaxWidth(),
                textAlign = TextAlign.Center,
                text = stringResource(id = R.string.otp_description)
            )
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.height_forty)))
            OTPInputField(onOtpComplete = {})


//            OTPTextField(
//                value = otp, // Initial value
//                onTextChanged = { otp = it },
//                numDigits = 4, // Number of digits in OTP
//                isMasked = true, // Mask digits for security
//                digitContainerStyle = OtpTextFieldDefaults.outlinedContainer(), // Choose style (outlined or underlined)
//                textStyle = MaterialTheme.typography.titleLarge, // Configure text style
//                isError = false // Indicate whether the OTP field is in an error state
//            )

            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.height_forty)))

            PrimaryButton(title = stringResource(id = R.string.submit),
                enabled = viewModel.isPhoneNumberFieldValid(),
                onButtonClicked = {
                    viewModel.onEvent((ForgotPasswordFormEvent.Submit))
                })


        }


    }


}

@Composable
fun OTPInputField(
    otpLength: Int = 6,
    onOtpComplete: (String) -> Unit
) {
    var otp by remember { mutableStateOf("") }
    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        for (i in 0 until otpLength) {
            OutlinedTextField(
                value = otp.getOrNull(i)?.toString() ?: "",
                onValueChange = { newChar ->
                    if (newChar.length == 1) {
                        otp = otp.substring(0, i) + newChar + otp.substring(i + 1)
                        if (otp.length == otpLength) {
                            onOtpComplete(otp)
                        }
                    }
                },
                modifier = Modifier.width(50.dp),
                singleLine = true,
                maxLines = 1,
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number
                )
            )
        }
    }
}





