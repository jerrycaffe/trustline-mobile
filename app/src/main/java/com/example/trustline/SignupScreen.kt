package com.example.trustline

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
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp

@Composable
fun SignupScreen(modifier: Modifier = Modifier) {
    var email by rememberSaveable { mutableStateOf("") }
    var phoneNumber by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(dimensionResource(id = R.dimen.padding_medium))

    ) {

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
                text = "create an account"
            )
            Text(
                text = "Enter your details to sign up to this app"
            )
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.height_forty)))
            Column(
                verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.space_ten)),
            ) {
                InputTextBox(email, "Email", onValueChanged = { })
                InputTextBox(phoneNumber, "Phone number", onValueChanged = { })
                InputTextBox(password, "Password", onValueChanged = { })

                PrimaryButton(title = "Sign up", enabled = false, onButtonClicked = {})
            }
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.height_twenty_four)))
            Column(
                verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.height_twenty_four))
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
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
                        text = "or continue with"
                    )
                    Divider(
                        modifier = Modifier
                            .weight(1f)
                            .height(1.dp)
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                )
                {
                    Image(
                        painter = painterResource(id = R.drawable.google_icon),
                        contentDescription = "Trustline logo"
                    )
                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        buildAnnotatedString {
                            withStyle(style = SpanStyle(colorResource(id = R.color.deep_grey)))
                            { append("By clicking continue, you agree to our ") }
                            append("Terms")
                        }
                    )
                    Text(
                        buildAnnotatedString {
                            append("of Service")
                            withStyle(style = SpanStyle(color = colorResource(id = R.color.deep_grey))) {
                                append(
                                    " and "
                                )
                            }
                            append("Privacy Policy")
                        }
                    )
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

@Composable
fun InputTextBox(value: String, placeHolder: String, onValueChanged: () -> Unit) {
    BasicTextField(
        value = value,
        onValueChange = { onValueChanged },
        decorationBox = { innerTextField ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(
                        1.dp,
                        Color.LightGray,
                        MaterialTheme.shapes.small
                    )
                    .padding(horizontal = dimensionResource(id = R.dimen.padding_medium))
                    .height(dimensionResource(id = R.dimen.height_semi_tall)),
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (value.isEmpty()) {
                    Text(
                        color = colorResource(id = R.color.deep_grey),
                        text = placeHolder
                    )
                }
                innerTextField()

            }
        })
}