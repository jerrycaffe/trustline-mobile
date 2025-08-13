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
import com.example.trustline.data.ApiResult
import com.example.trustline.data.AuthRepository
import com.example.trustline.data.RegisterUserRequest
import com.example.trustline.data.RegisterUserRes
import com.example.trustline.utils.ValidateEmail
import com.example.trustline.utils.ValidatePassword
import com.example.trustline.utils.ValidatePhoneNumber
import com.example.trustline.utils.toTitleCase
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
        return state.email != "" && state.phoneNumber != "" && state.password != "" && state.emailError == null && state.phoneNumberError == null && state.passwordError == null

    }

    private fun onPhoneNumberChanged(phoneNumber: String) {
        val phoneNumberError = validatePhoneNumber.execute(phoneNumber).errorMessage
        state = state.copy(
            phoneNumber = phoneNumber,
            phoneNumberError = phoneNumberError,
            isAllFieldValid = isAllFieldValid()
        )
    }

    private fun onEmailChanged(email: String) {
        val emailError = validateEmail.execute(email).errorMessage
        state = state.copy(
            email = email, emailError = emailError, isAllFieldValid = isAllFieldValid()
        )
    }

    private fun onChangePassword(password: String) {
        val passwordError = validatePassword.execute(password).errorMessage
        state = state.copy(
            password = password, passwordError = passwordError, isAllFieldValid = isAllFieldValid()
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
                emailError = null, passwordError = null, phoneNumberError = null
            )
        }


//        viewModelScope.launch { validationEventChannel.send(ValidationEvent.Success) }

        viewModelScope.launch {
//Clear previous backend error
            state = state.copy(apiError = null, isLoading = true)

            val request = RegisterUserRequest(
                email = state.email, password = state.password, phoneNumber = state.phoneNumber
            )

            val result = authRepository.registerUser(request)
            when (result) {
                is ApiResult.Error -> {
                    state = state.copy(apiError = result.message.toTitleCase(), isLoading = false)
                }

                is ApiResult.Success -> {
                    Log.i("REGISTER_SUCCESS_RESPONSE", result.data.toString())
                    validationEventChannel.send(ValidationEvent.Success(result.data))
                    state =
                        state.copy(apiError = null, isLoading = false, registeredUser = result.data)

                }
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
    data class Success(val registerUserRes: RegisterUserRes) : ValidationEvent()
}



