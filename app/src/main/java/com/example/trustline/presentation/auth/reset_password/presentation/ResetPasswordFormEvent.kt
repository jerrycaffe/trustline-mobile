package com.example.trustline.presentation.auth.reset_password.presentation

import java.util.UUID


sealed class ResetPasswordFormEvent {
    data class PasswordChanged(val password: String) : ResetPasswordFormEvent()
    data class ConfirmPasswordChanged(val confirmPassword: String) : ResetPasswordFormEvent()
    data class Submit(val userId: UUID, val otpId: UUID) : ResetPasswordFormEvent()
}