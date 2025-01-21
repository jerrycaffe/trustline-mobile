package com.example.trustline.presentation.auth.forgot_password.presentation

data class ForgotPasswordFormState(
    val phoneNumber: String = "",
    val phoneNumberError: String? = null
)

