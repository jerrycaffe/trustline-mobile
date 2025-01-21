package com.example.trustline.presentation.auth.login.presentation

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.trustline.utils.ValidateEmail
import com.example.trustline.utils.ValidatePassword
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class LoginViewModel(
    private val validateEmail: ValidateEmail = ValidateEmail(),
    private val validatePassword: ValidatePassword = ValidatePassword()
) : ViewModel() {
    var state by mutableStateOf(LoginFormState())

    private val validationEventChannel = Channel<ValidationEvent>()

    val validationEvents = validationEventChannel.receiveAsFlow()


    fun isAllFieldMatch(): Boolean {
        return state.email != ""
                && state.password != ""
                && state.emailError == null
                && state.passwordError == null
    }

    private fun onEmailChanged(email: String) {
        val emailError = validateEmail.execute(email).errorMessage
        state =
            state.copy(
                email = email,
                emailError = emailError
            )
    }

    private fun onPasswordPasswordChanged(password: String) {
        val passwordError = validatePassword.execute(password).errorMessage
        state =
            state.copy(
                password = password,
                passwordError = passwordError
            )
    }

    fun onEvent(event: LoginFormEvent) {
        when (event) {
            is LoginFormEvent.EmailChanged -> onEmailChanged(event.email)

            is LoginFormEvent.PasswordChanged -> onPasswordPasswordChanged(event.password)

            LoginFormEvent.Submit -> submitData()
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

