package com.example.trustline.presentation.auth.register.presentation

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.trustline.utils.ValidateEmail
import com.example.trustline.utils.ValidatePassword
import com.example.trustline.utils.ValidatePhoneNumber
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

                //validate the email field
                //check if all the field are valid and turn on the submit the button
                val emailError = validateEmail.execute(state.email).errorMessage
                val allFieldsValid: Boolean =
                    if (state.email != ""
                        && state.phoneNumber != ""
                        && state.password != ""
                        && state.emailError == null
                        && state.phoneNumberError == null
                        && state.passwordError == null
                    ) true else false
                state =
                    state.copy(
                        email = event.email,
                        emailError = emailError,
                        isAllFieldValid = allFieldsValid
                    )
            }

            is RegistrationFormEvent.PhoneNumberChanged -> {
                val phoneNumberError = validatePhoneNumber.execute(state.phoneNumber).errorMessage
                val allFieldsValid: Boolean =
                    if (state.email != ""
                        && state.phoneNumber != ""
                        && state.password != ""
                        && state.emailError == null
                        && state.phoneNumberError == null
                        && state.passwordError == null
                    ) true else false
                state =
                    state.copy(
                        phoneNumber = event.phoneNumber,
                        phoneNumberError = phoneNumberError,
                        isAllFieldValid = allFieldsValid
                    )

            }

            is RegistrationFormEvent.PasswordChanged -> {
                val passwordError = validatePassword.execute(state.password).errorMessage
                val allFieldsValid: Boolean =
                    if (state.email != ""
                        && state.phoneNumber != ""
                        && state.password != ""
                        && state.emailError == null
                        && state.phoneNumberError == null
                        && state.passwordError == null
                    ) true else false
                state =
                    state.copy(
                        password = event.password,
                        passwordError = passwordError,
                        isAllFieldValid = allFieldsValid
                    )
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
        Log.d("STATE", state.email)
        Log.d("STATE", state.password)
        Log.d("STATE", state.phoneNumber)
        println(state.phoneNumberError)

        viewModelScope.launch { validationEventChannel.send(ValidationEvent.Success) }

    }

    sealed class ValidationEvent {
        data object Success : ValidationEvent()
    }

}

