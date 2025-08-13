package com.example.trustline.presentation.auth.otp.presentation

sealed class OtpFormEvent {
    data class OtpChanged(val otp: String) : OtpFormEvent()
    data class Submit(val otp: String) : OtpFormEvent()
}