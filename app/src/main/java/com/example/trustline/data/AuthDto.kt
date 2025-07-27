package com.example.trustline.data

import kotlinx.serialization.Serializable
import java.util.UUID

data class RegisterUserRes(
    val id: UUID, val email: String, val phoneNumber: String, val emailVerified: Boolean
)

@Serializable
data class RegisterUserRequest(
    val email: String, val password: String, val phoneNumber: String
)
