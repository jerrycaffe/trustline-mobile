package com.example.trustline.data


interface AuthRepository {
    suspend fun registerUser(registerUserRequest: RegisterUserRequest): ApiResult<RegisterUserRes>

    suspend fun otpValidation(otpValidationRequest: OtpValidationRequest): ApiResult<OtpValidationResponse>
}

class AuthRepositoryImpl(
    private val api: ApiServiceImpl
) : AuthRepository {
    override suspend fun registerUser(registerUserRequest: RegisterUserRequest): ApiResult<RegisterUserRes> {
        return api.makePostCall(
            "/api/v1/auth/register",
            registerUserRequest,
            RegisterUserRes::class
        )
    }

    override suspend fun otpValidation(otpValidationRequest: OtpValidationRequest): ApiResult<OtpValidationResponse> {
        return api.makePostCall(
            "/api/v1/auth/verify-otp",
            otpValidationRequest,
            OtpValidationResponse::class
        )
    }
}



