package com.example.trustline.data

import okhttp3.MultipartBody
import okhttp3.RequestBody

interface AuthRepository {
    suspend fun registerUser(registerUserRequest: RegisterUserRequest): ApiResult<RegisterUserRes>

    suspend fun otpValidation(otpValidationRequest: OtpValidationRequest): ApiResult<OtpValidationResponse>

    suspend fun loginUser(loginRequest: LoginRequest): ApiResult<LoginResponse>

    suspend fun forgotPassword(forgotPasswordRequest: ForgotPasswordRequest): ApiResult<ForgotPasswordResponse>

    suspend fun resendOtp(resendOtpRequest: ResendOtpRequest): ApiResult<ResendOtpResponse>

    suspend fun resetPassword(resetPasswordRequest: ResetPasswordRequest): ApiResult<ResetPasswordResponse>

    suspend fun getIncidentTypes(accessToken: String): ApiResult<List<IncidentType>>

    suspend fun getGbvResources(
        institutionId: String,
        offset: Int,
        limit: Int
    ): ApiResult<GbvResourceListResponse>

    suspend fun submitCase(
        accessToken: String,
        caseBody: RequestBody,
        files: List<MultipartBody.Part>
    ): ApiResult<Unit>

    suspend fun getCases(
        accessToken: String,
        offset: Int,
        limit: Int
    ): ApiResult<CaseListResponse>
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

    override suspend fun resendOtp(resendOtpRequest: ResendOtpRequest): ApiResult<ResendOtpResponse> {
        return api.makePostCall(
            "auth/resend-otp",
            resendOtpRequest,
            ResendOtpResponse::class
        )
    }

    override suspend fun resetPassword(resetPasswordRequest: ResetPasswordRequest): ApiResult<ResetPasswordResponse> {
        return api.makePostCall(
            "auth/reset-password",
            resetPasswordRequest,
            ResetPasswordResponse::class
        )
    }

    override suspend fun getIncidentTypes(accessToken: String): ApiResult<List<IncidentType>> {
        return api.makeGetCall(
            "/api/v1/incident-types",
            accessToken
        )
    }

    override suspend fun getGbvResources(
        institutionId: String,
        offset: Int,
        limit: Int
    ): ApiResult<GbvResourceListResponse> {
        val url = "/api/v1/resources?institutionId=$institutionId&offset=$offset&limit=$limit"
        return api.makePublicGetCall(url)
    }

    override suspend fun submitCase(
        accessToken: String,
        caseBody: RequestBody,
        files: List<MultipartBody.Part>
    ): ApiResult<Unit> {
        return api.makeMultipartPostCall(
            accessToken = accessToken,
            caseBody = caseBody,
            files = files
        )
    }

    override suspend fun getCases(
        accessToken: String,
        offset: Int,
        limit: Int
    ): ApiResult<CaseListResponse> {
        val result = api.makeMyCasesGetCall(accessToken)
        return when (result) {
            is ApiResult.Success -> {
                val payload = result.data
                ApiResult.Success(
                    data = CaseListResponse(
                        content = payload?.content?.map {
                            CaseApiModel(
                                id = it.id,
                                caseNumber = it.caseNumber,
                                incidentType = it.incidentType,
                                status = it.status,
                                location = it.location,
                                description = it.description,
                                dateOfIncident = it.dateOfIncident,
                                createdAt = it.createdAt,
                                updatedAt = it.updatedAt,
                                allUnitsInvolved = it.allUnitsInvolved
                            )
                        },
                        count = payload?.count ?: 0,
                        total = payload?.total ?: 0,
                        last = payload?.last ?: true
                    ),
                    message = result.message,
                    success = result.success,
                    code = result.code
                )
            }

            is ApiResult.Error -> ApiResult.Error(result.message, result.code)
        }
    }
}
