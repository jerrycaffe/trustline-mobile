package com.example.trustline.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.trustline.domain.use_cases.ValidateEmail
import com.example.trustline.domain.use_cases.ValidatePassword
import com.example.trustline.domain.use_cases.ValidatePhoneNumber
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class SignupViewModel(
    private val validateEmail: ValidateEmail = ValidateEmail(),
    private val validatePhoneNumber: ValidatePhoneNumber = ValidatePhoneNumber(),
    private val validatePassword: ValidatePassword = ValidatePassword()
) : ViewModel() {
    var state by mutableStateOf(RegistrationFormState())
    private val validationEventChannel = Channel<ValidationEvent>()

    val validationEvents = validationEventChannel.receiveAsFlow()

    fun onEvent(event: RegistrationFormEvent) {
        when (event) {
            is RegistrationFormEvent.EmailChanged -> {
                state = state.copy(email = event.email)
            }

            is RegistrationFormEvent.PhoneNumberChanged -> {
                state = state.copy(phoneNumber = event.phoneNumber)
            }

            is RegistrationFormEvent.PasswordChanged -> {
                state = state.copy(password = event.password)
            }

            RegistrationFormEvent.Submit -> {
                submitData()
            }
        }
    }

    private fun submitData() {
        val emailResult = validateEmail.execute(state.email)
        val phoneNumberResult = validatePhoneNumber.execute(state.phoneNumber)
        val passwordResult = validatePassword.execute(state.password)

        val hasError = listOf(
            emailResult, passwordResult, phoneNumberResult
        ).any { !it.successful }

        if (hasError) {
            state = state.copy(
                emailError = emailResult.errorMessage,
                passwordError = passwordResult.errorMessage,
                phoneNumberError = phoneNumberResult.errorMessage
            )

        } else {
            state = state.copy(
                emailError = null,
                passwordError = null,
                phoneNumberError = null
            )
        }

        viewModelScope.launch { validationEventChannel.send(ValidationEvent.Success) }

    }

    sealed class ValidationEvent {
        data object Success : ValidationEvent()
    }

}

