package com.example.trustline.presentation.auth.forgot_password.presentation


sealed class ForgotPasswordFormEvent {
    data class PhoneNumberChanged(val email: String) : ForgotPasswordFormEvent()
    data object Submit : ForgotPasswordFormEvent()
}