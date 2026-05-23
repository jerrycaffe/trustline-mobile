package com.example.trustline.data

import com.google.gson.Gson
import okhttp3.ResponseBody
import java.util.UUID

data class RegisterUserRes(
    val otpId: UUID?,
    val email: String?,
    val phoneNumber: String?,
    val userId: UUID?,
    val emailVerified: Boolean
)

data class RegisterUserRequest(
    val email: String, val password: String, val phoneNumber: String, val institutionId: UUID
)

sealed class ApiResult<out T> {
    data class Success<out T>(
        val data: T?,
        val message: String?,
        val success: Boolean,
        val code: String?
    ) : ApiResult<T>()

    data class Error(val message: String, val code: String? = null) : ApiResult<Nothing>()
}

data class BaseResponse<T>(
    val data: T?,
    val message: String?,
    val success: Boolean,
    val code: String?
)


data class ErrorResponse(
    val code: String,
    val message: String,
    val success: Boolean,
    val data: Any? = null
)

fun parseError(responseBody: ResponseBody?): ErrorResponse? {
    return try {
        val gson = Gson()
        val rawJson: String? = responseBody?.string()
        gson.fromJson(rawJson, ErrorResponse::class.java)
    } catch (e: Exception) {
        null
    }
}

data class OtpValidationRequest(
    val verificationId: String, val userId: UUID
)

data class OtpValidationResponse(val message: String)

data class LoginRequest(
    val userName: String,
    val password: String,
    val institutionId: UUID
)

data class LoginResponse(
    val accessToken: String,
    val userDetails: UserDetails,
    val otpId: UUID?,
    val userId: UUID?
)

data class UserDetails(
    val otpId: UUID?,
    val userId: UUID?,
    val email: String?,
    val phoneNumber: String?,
    val firstName: String?,
    val lastName: String?,
    val gender: String?,
    val status: String?,
    val isAccountVerified: Boolean
)

data class ForgotPasswordRequest(
    val email: String,
    val institutionId: UUID
)

data class ForgotPasswordResponse(
    val otpId: UUID,
    val userId: UUID
)

data class ResetPasswordRequest(
    val newPassword: String,
    val userId: UUID,
    val institutionId: UUID,
    val otpId: UUID
)

data class ResetPasswordResponse(
    val message: String
)

data class ResendOtpRequest(
    val email: String,
    val institutionId: UUID
)

data class ResendOtpResponse(
    val otpId: UUID,
    val userId: UUID
)
