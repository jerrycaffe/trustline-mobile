package com.example.trustline.presentation.auth.register.presentation

sealed class RegistrationFormEvent {
    data class EmailChanged(val email: String) : RegistrationFormEvent()
    data class PhoneNumberChanged(val phoneNumber: String) : RegistrationFormEvent()
    data class PasswordChanged(val password: String) : RegistrationFormEvent()
    data object Submit : RegistrationFormEvent()
}