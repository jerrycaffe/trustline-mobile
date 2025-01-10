package com.example.trustline.presentation.auth.login.presentation

data class LoginFormState(
    val email: String = "",
    val emailError: String? = null,
    val password: String = "",
    val passwordError: String? = null,
    val isAllFieldValid: Boolean = false
)
