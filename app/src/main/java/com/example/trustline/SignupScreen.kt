package com.example.trustline

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun SignupScreen(modifier: Modifier = Modifier) {
    Column {
        TrustlineTitle()
        Column {
            Text(text = "Create an account")
            Text(text = "Enter your details to sign up to this app")

            TextField(value = "", onValueChange = {}, placeholder = { Text(text = "Email") })
            TextField(value = "", onValueChange = {}, placeholder = { Text(text = "Phone number") })
            TextField(value = "", onValueChange = {}, placeholder = { Text(text = "Password") })

            PrimaryButton(title = "Sign up", onButtonClicked = {/*TODO: implement submit here*/ })

            Row {
                Divider()
                Text("or continue with")
                Divider()
            }
            Row {
                //TODO: social media
            }

            Text("By clicking continue, you agree to our Terms")
            Text(text = "of Service and Privacy Policy")

        }
        Text(text = "Already have an account? Login")

    }
}