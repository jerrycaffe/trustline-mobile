package com.example.trustline.presentation.auth.reset_password.presentation

data class ResetPasswordFormState(
    val password: String = "",
    val passwordError: String? = null,
    val confirmPassword: String = "",
    val confirmPasswordError: String? = null
)

val ResetPasswordFormState.isAllFieldValid: Boolean get() = password != "" && confirmPassword != "" && password == confirmPassword && passwordError == null && confirmPasswordError == null
