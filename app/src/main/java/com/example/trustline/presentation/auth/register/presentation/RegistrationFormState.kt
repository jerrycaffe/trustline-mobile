package com.example.trustline.presentation.auth.register.presentation

data class RegistrationFormState(
    val email: String = "",
    val emailError: String? = null,
    val password: String = "",
    val passwordError: String? = null,
    val phoneNumber: String = "",
    val phoneNumberError: String? = null
)
