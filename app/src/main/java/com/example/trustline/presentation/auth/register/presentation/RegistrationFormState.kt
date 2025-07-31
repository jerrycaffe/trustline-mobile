package com.example.trustline.presentation.auth.register.presentation

import com.example.trustline.data.RegisterUserRes

data class RegistrationFormState(
    val email: String = "",
    val emailError: String? = null,
    val password: String = "",
    val passwordError: String? = null,
    val phoneNumber: String = "",
    val isLoading: Boolean = false,
    val apiError: String? = null,
    val phoneNumberError: String? = null,
    val isAllFieldValid: Boolean = false,
    val registeredUser: RegisterUserRes? = null
)
