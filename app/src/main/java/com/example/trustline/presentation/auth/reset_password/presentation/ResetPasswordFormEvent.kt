package com.example.trustline.presentation.auth.reset_password.presentation


sealed class ResetPasswordFormEvent {
    data class PasswordChanged(val password: String) : ResetPasswordFormEvent()
    data class ConfirmPasswordChanged(val confirmPassword: String) : ResetPasswordFormEvent()
    data object Submit : ResetPasswordFormEvent()
}