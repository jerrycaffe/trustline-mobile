package com.example.trustline.presentation

sealed class RegistrationFormEvent {
    data class EmailChanged(val email: String) : RegistrationFormEvent()
    data class PhoneNumberChanged(val phoneNumber: String) : RegistrationFormEvent()
    data class PasswordChanged(val password: String) : RegistrationFormEvent()
    object Submit : RegistrationFormEvent()
}