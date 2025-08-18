package com.example.trustline.presentation.auth.otp.presentation

import java.util.UUID

data class OtpFormState(
    val otpValue: String = "",
    val timerValue: String = "",
    var userId: UUID? = null,
    val otpError: String? = null,
    val isAllFieldValid: Boolean = false,
    val otpCount: Int = 6,
    val apiError: String? = null,
    val isLoading: Boolean = false
)
