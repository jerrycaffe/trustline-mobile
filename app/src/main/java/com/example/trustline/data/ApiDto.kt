package com.example.trustline.data

import com.google.gson.Gson
import com.google.gson.TypeAdapter
import okhttp3.ResponseBody
import java.util.UUID

data class RegisterUserRes(
    val id: UUID,
    val email: String,
    val phoneNumber: String,
    val emailVerified: Boolean
)

data class RegisterUserRequest(
    val email: String, val password: String, val phoneNumber: String
)

sealed class ApiResult<out T> {
    data class Success<out T>(val data: T) : ApiResult<T>()
    data class Error(val message: String, val code: Int? = null) : ApiResult<Nothing>()
}


data class ErrorResponseWrapper(
    val error: ErrorResponse
)

data class ErrorResponse(
    val code: String,
    val message: String
)

fun parseError(responseBody: ResponseBody?): ErrorResponse? {
    return try {
        val gson = Gson()
        val adapter: TypeAdapter<ErrorResponseWrapper> =
            gson.getAdapter(ErrorResponseWrapper::class.java)
        val rawJson: String? = responseBody?.string()
        val wrapper: ErrorResponseWrapper? = adapter.fromJson(rawJson)
        wrapper?.error
    } catch (e: Exception) {
        null
    }
}

data class OtpValidationRequest(
    val verificationId: String, val userId: UUID
)

data class OtpValidationResponse(val message: String)