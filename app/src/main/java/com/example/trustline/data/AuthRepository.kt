package com.example.trustline.data


interface AuthRepository {
    suspend fun registerUser(registerUserRequest: RegisterUserRequest): RegisterUserRes
}

class AuthRepositoryImpl(
    private val api: AuthService
) : AuthRepository {
    override suspend fun registerUser(registerUserRequest: RegisterUserRequest): RegisterUserRes {
        return api.registerUser(registerUserRequest)
    }
}