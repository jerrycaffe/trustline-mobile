package com.example.trustline.presentation.auth.login.presentation

sealed class ForgotPasswordFormEvent {
    data class EmailChanged(val email: String) : LoginFormEvent()
    data class PasswordChanged(val password: String) : LoginFormEvent()
    data object Submit : LoginFormEvent()
}