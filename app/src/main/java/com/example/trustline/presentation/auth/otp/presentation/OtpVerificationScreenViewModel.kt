package com.example.trustline.presentation.auth.forgot_password.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.trustline.utils.ValidatePhoneNumber
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class OtpVerificationScreenViewModel(
    private val validateEmail: ValidatePhoneNumber = ValidatePhoneNumber()
) : ViewModel() {
    var state by mutableStateOf(ForgotPasswordFormState())

    private val validationEventChannel = Channel<ValidationEvent>()

    val validationEvents = validationEventChannel.receiveAsFlow()

    fun isPhoneNumberFieldValid(): Boolean =
        state.phoneNumber != "" && state.phoneNumberError == null

    fun onEvent(event: ForgotPasswordFormEvent) {
        when (event) {
            is ForgotPasswordFormEvent.PhoneNumberChanged -> {
                val phoneNumberError = validateEmail.execute(event.phoneNumber).errorMessage

                state =
                    state.copy(
                        phoneNumber = event.phoneNumber,
                        phoneNumberError = phoneNumberError
                    )
            }

            ForgotPasswordFormEvent.Submit -> {
                submitData()
            }
        }
    }

    private fun submitData() {
        val phoneNumberResult = validateEmail.execute(state.phoneNumber)

        state = if (!phoneNumberResult.successful) {
            state.copy(
                phoneNumberError = phoneNumberResult.errorMessage
            )

        } else {
            state.copy(
                phoneNumberError = null
            )
        }
        viewModelScope.launch { validationEventChannel.send(ValidationEvent.Success) }
    }

    sealed class ValidationEvent {
        data object Success : ValidationEvent()
    }

}

