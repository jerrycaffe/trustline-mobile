package com.example.trustline.presentation.auth.login.presentation

data class ForgotPasswordFormState(
    val email: String = "",
    val emailError: String? = null,
    val password: String = "",
    val passwordError: String? = null
)

val ForgotPasswordFormState.isAllFieldValid: Boolean get() = email != "" && password != "" && emailError == null && passwordError == null
