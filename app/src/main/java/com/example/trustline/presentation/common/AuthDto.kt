package com.example.trustline.presentation.common

import java.util.UUID

data class OtpValidationContent(
    var userId: UUID,
    val email: String,
    val otpId: UUID? = null,
    val intendedScreen: String
)
