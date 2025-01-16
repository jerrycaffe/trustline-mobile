package com.example.trustline.presentation.auth.forgot_password.presentation

data class ForgotPasswordFormState(
    val email: String = "",
    val emailError: String? = null
)

val ForgotPasswordFormState.isFieldValid: Boolean get() = email != "" && emailError == null
