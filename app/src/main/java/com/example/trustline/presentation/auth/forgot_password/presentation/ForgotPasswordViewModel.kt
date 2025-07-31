package com.example.trustline.presentation.auth.forgot_password.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.trustline.utils.ValidateEmail
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class ForgotPasswordViewModel(
    private val validateEmail: ValidateEmail = ValidateEmail()
) : ViewModel() {
    var state by mutableStateOf(ForgotPasswordFormState())

    private val validationEventChannel = Channel<ValidationEvent>()
    val validationEvents = validationEventChannel.receiveAsFlow()

    fun isEmailFieldValid(): Boolean =
        state.email != "" && state.emailError == null

    fun onEvent(event: ForgotPasswordFormEvent) {
        when (event) {
            is ForgotPasswordFormEvent.PhoneNumberChanged -> {
                val emailError = validateEmail.execute(event.email).errorMessage

                state =
                    state.copy(
                        email = event.email,
                        emailError = emailError
                    )
            }

            ForgotPasswordFormEvent.Submit -> {
                submitData()
            }
        }
    }

    private fun submitData() {
        val emailResult = validateEmail.execute(state.email)

        state = if (!emailResult.successful) {
            state.copy(
                emailError = emailResult.errorMessage
            )

        } else {
            state.copy(
                emailError = null
            )
        }
        viewModelScope.launch { validationEventChannel.send(ValidationEvent.Success) }
    }

    sealed class ValidationEvent {
        data object Success : ValidationEvent()
    }

}

