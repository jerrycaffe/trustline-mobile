package com.example.trustline.presentation.auth.otp.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.trustline.MainViewModel
import com.example.trustline.R
import com.example.trustline.presentation.common.PrimaryButton

@Composable
fun OtpVerificationScreen(navController: NavHostController, globalViewModel: MainViewModel) {
    val viewModel = viewModel<OtpVerificationScreenViewModel>()
    val state = viewModel.state
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current
    var otpValue by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
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
                text = stringResource(
                    id = R.string.otp_description,
                    "${globalViewModel.registeredUser?.email}"
                )
            )
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.height_forty)))

            OtpTextField(
                otpText = otpValue,
                onOtpTextChange = { value, _ ->
                    otpValue = value
                }
            )

            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.height_thirty_two)))
            Text(text = "${viewModel.timerText.value} sec")
            Spacer(modifier = Modifier.height(20.dp))
            Row {
                Text(text = "Didn’t receive code? ")
                Text(
                    text = "Resend",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.clickable { viewModel.resetCountDownTimer() })
            }

            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.height_thirty_two)))

            PrimaryButton(
                title = stringResource(id = R.string.submit),
                enabled = otpValue.length == 6,
                onButtonClicked = {
//                    viewModel.onEvent((ForgotPasswordFormEvent.Submit))
                    viewModel.startCountDownTimer()
                })


        }


    }


}

@Composable
fun OtpTextField(
    modifier: Modifier = Modifier,
    otpText: String,
    otpCount: Int = 6,
    onOtpTextChange: (String, Boolean) -> Unit
) {
    LaunchedEffect(Unit) {
        if (otpText.length > otpCount) {
            throw IllegalArgumentException("Otp text value must not have more than otpCount: $otpCount characters")
        }
    }

    BasicTextField(
        modifier = modifier,
        value = TextFieldValue(otpText, selection = TextRange(otpText.length)),
        onValueChange = {
            if (it.text.length <= otpCount) {
                onOtpTextChange.invoke(it.text, it.text.length == otpCount)
            }
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
        decorationBox = {
            Row(
                horizontalArrangement = Arrangement.Center
            ) {
                repeat(otpCount) { index ->
                    CharView(
                        index = index,
                        text = otpText
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                }
            }
        }
    )
}

@Composable
private fun CharView(
    index: Int,
    text: String
) {
    val isFocused = text.length == index
    val char = when {
        index == text.length -> "-"
        index > text.length -> ""
        else -> text[index].toString()
    }
    Text(
        modifier = Modifier
            .width(42.dp)
            .height(42.dp)
            .border(
                1.5.dp, when {
                    isFocused || text.isNotBlank() -> colorResource(id = R.color.primary)
                    else -> Color.LightGray
                }, RoundedCornerShape(10.dp)
            )
            .padding(2.dp),
        text = char,
        style = MaterialTheme.typography.titleLarge,
        color = if (isFocused || text.isNotBlank()) {
            Color.DarkGray
        } else {
            Color.LightGray
        },
        fontSize = 24.sp,
        textAlign = TextAlign.Center
    )
}





