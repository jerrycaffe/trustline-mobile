package com.example.trustline.presentation.auth.register.presentation

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.trustline.TrustlineApplication
import com.example.trustline.data.AuthRepository
import com.example.trustline.data.RegisterUserRequest
import com.example.trustline.utils.ValidateEmail
import com.example.trustline.utils.ValidatePassword
import com.example.trustline.utils.ValidatePhoneNumber
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch


class SignupViewModel(
    private val validateEmail: ValidateEmail = ValidateEmail(),
    private val validatePhoneNumber: ValidatePhoneNumber = ValidatePhoneNumber(),
    private val validatePassword: ValidatePassword = ValidatePassword(),
    private val authRepository: AuthRepository

) : ViewModel() {
    var state by mutableStateOf(RegistrationFormState())
    private val validationEventChannel = Channel<ValidationEvent>()

    val validationEvents = validationEventChannel.receiveAsFlow()

    fun isAllFieldValid(): Boolean {
        return state.email != ""
                && state.phoneNumber != ""
                && state.password != ""
                && state.emailError == null
                && state.phoneNumberError == null
                && state.passwordError == null

    }

    private fun onPhoneNumberChanged(phoneNumber: String) {
        val phoneNumberError = validatePhoneNumber.execute(phoneNumber).errorMessage
        state =
            state.copy(
                phoneNumber = phoneNumber,
                phoneNumberError = phoneNumberError,
                isAllFieldValid = isAllFieldValid()
            )
    }

    private fun onEmailChanged(email: String) {
        val emailError = validateEmail.execute(email).errorMessage
        state =
            state.copy(
                email = email,
                emailError = emailError,
                isAllFieldValid = isAllFieldValid()
            )
    }

    private fun onChangePassword(password: String) {
        val passwordError = validatePassword.execute(password).errorMessage
        state =
            state.copy(
                password = password,
                passwordError = passwordError,
                isAllFieldValid = isAllFieldValid()
            )
    }

    fun onEvent(event: RegistrationFormEvent) {
        when (event) {
            is RegistrationFormEvent.EmailChanged -> onEmailChanged(event.email)

            is RegistrationFormEvent.PhoneNumberChanged -> onPhoneNumberChanged(event.phoneNumber)

            is RegistrationFormEvent.PasswordChanged -> onChangePassword(event.password)

            RegistrationFormEvent.Submit -> submitData()

        }
    }

    private fun submitData() {
        val emailResult = validateEmail.execute(state.email)
        val phoneNumberResult = validatePhoneNumber.execute(state.phoneNumber)
        val passwordResult = validatePassword.execute(state.password)
//        state = state.copy(apiError = "")

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


//        viewModelScope.launch { validationEventChannel.send(ValidationEvent.Success) }

        viewModelScope.launch {
            try {
                val request = RegisterUserRequest(
                    email = state.email,
                    password = state.password,
                    phoneNumber = state.phoneNumber
                )
                Log.d("REGISTER", request.toString())
                val response = authRepository.registerUser(request)

                if (response.id != null) {
                    validationEventChannel.send(ValidationEvent.Success)
                } else {
//                     handle API errors like email already taken, etc.
                    state = state.copy(apiError = "Registration failed")
                    Log.e("Registration error", "error while registering user")
                }
            } catch (e: Exception) {
//                state = state.copy(apiError = "Unexpected error: ${e.localizedMessage}")
                e.message?.let { Log.d("ERROR REGISTER", it) }
            }
        }
    }


    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as TrustlineApplication)
                val authRepository = application.container.authRepository
                SignupViewModel(authRepository = authRepository)
            }
        }
    }

}

sealed class ValidationEvent {
    data object Success : ValidationEvent()
}



