package com.example.trustline.data


interface AuthRepository {
    suspend fun registerUser(registerUserRequest: RegisterUserRequest): ApiResult<RegisterUserRes>

    suspend fun otpValidation(otpValidationRequest: OtpValidationRequest): ApiResult<OtpValidationResponse>

    suspend fun loginUser(loginRequest: LoginRequest): ApiResult<LoginResponse>

    suspend fun forgotPassword(forgotPasswordRequest: ForgotPasswordRequest): ApiResult<ForgotPasswordResponse>
}

class AuthRepositoryImpl(
    private val api: ApiServiceImpl
) : AuthRepository {
    override suspend fun registerUser(registerUserRequest: RegisterUserRequest): ApiResult<RegisterUserRes> {
        return api.makePostCall(
            "auth/register",
            registerUserRequest,
            RegisterUserRes::class
        )
    }

    override suspend fun otpValidation(otpValidationRequest: OtpValidationRequest): ApiResult<OtpValidationResponse> {
        return api.makePostCall(
            "auth/verify-otp",
            otpValidationRequest,
            OtpValidationResponse::class
        )
    }

    override suspend fun loginUser(loginRequest: LoginRequest): ApiResult<LoginResponse> {
        return api.makePostCall(
            "auth/login",
            loginRequest,
            LoginResponse::class
        )
    }

    override suspend fun forgotPassword(forgotPasswordRequest: ForgotPasswordRequest): ApiResult<ForgotPasswordResponse> {
        return api.makePostCall(
            "auth/forgot-password",
            forgotPasswordRequest,
            ForgotPasswordResponse::class
        )
    }
}



