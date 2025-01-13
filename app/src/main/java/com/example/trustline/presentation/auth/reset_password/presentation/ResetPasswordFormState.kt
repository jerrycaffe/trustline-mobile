package com.example.trustline.presentation.auth.login.presentation

data class ResetPasswordFormState(
    val email: String = "",
    val emailError: String? = null,
    val password: String = "",
    val passwordError: String? = null
)

val ResetPasswordFormState.isAllFieldValid: Boolean get() = email != "" && password != "" && emailError == null && passwordError == null
