package com.example.trustline.presentation.auth.login.presentation

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

class LoginViewModel(
    private val validateEmail: ValidateEmail = ValidateEmail(),
    private val validatePhoneNumber: ValidatePhoneNumber = ValidatePhoneNumber(),
    private val validatePassword: ValidatePassword = ValidatePassword()
) : ViewModel() {
    var state by mutableStateOf(LoginFormState())
    private val validationEventChannel = Channel<ValidationEvent>()

    val validationEvents = validationEventChannel.receiveAsFlow()

    fun onEvent(event: LoginFormEvent) {
        when (event) {
            is LoginFormEvent.EmailChanged -> {

                //validate the email field
                //check if all the field are valid and turn on the submit the button
                val emailError = validateEmail.execute(state.email).errorMessage
                val allFieldsValid: Boolean =
                    if (state.email != ""
                        && state.password != ""
                        && state.emailError == null
                        && state.passwordError == null
                    ) true else false
                state =
                    state.copy(
                        email = event.email,
                        emailError = emailError,
                        isAllFieldValid = allFieldsValid
                    )
            }


            is LoginFormEvent.PasswordChanged -> {
                val passwordError = validatePassword.execute(state.password).errorMessage
                val allFieldsValid: Boolean =
                    if (state.email != ""
                        && state.password != ""
                        && state.emailError == null
                        && state.passwordError == null
                    ) true else false
                state =
                    state.copy(
                        password = event.password,
                        passwordError = passwordError,
                        isAllFieldValid = allFieldsValid
                    )
            }

            LoginFormEvent.Submit -> {
                submitData()
            }
        }
    }

    private fun submitData() {
        val emailResult = validateEmail.execute(state.email)
        val passwordResult = validatePassword.execute(state.password)

        val hasError = listOf(
            emailResult, passwordResult
        ).any { !it.successful }

        if (hasError) {
            state = state.copy(
                emailError = emailResult.errorMessage,
                passwordError = passwordResult.errorMessage
            )

        } else {
            state = state.copy(
                emailError = null,
                passwordError = null
            )
        }
        Log.d("STATE", state.email)
        Log.d("STATE", state.password)

        viewModelScope.launch { validationEventChannel.send(ValidationEvent.Success) }

    }

    sealed class ValidationEvent {
        data object Success : ValidationEvent()
    }

}

