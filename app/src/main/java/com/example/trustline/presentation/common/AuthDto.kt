package com.example.trustline.presentation.common

import java.util.UUID

data class OtpValidationContent(
    var userId: UUID,
    val email: String,
    val otpType: OtpType
)

enum class OtpType {
    REGISTER, FORGOT_PASSWORD, RESET_PASSWORD
}