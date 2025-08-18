package com.example.trustline.presentation.auth.otp.presentation

import java.util.UUID

sealed class OtpFormEvent {
    data class OtpChanged(val otpValue: String) : OtpFormEvent()
    data class Submit(val otp: String, val userId: UUID) : OtpFormEvent()
    data object ResendOtp : OtpFormEvent()

}