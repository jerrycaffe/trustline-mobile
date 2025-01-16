package com.example.trustline.presentation.auth.forgot_password.presentation

import android.util.Log
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

    fun onEvent(event: ForgotPasswordFormEvent) {
        when (event) {
            is ForgotPasswordFormEvent.EmailChanged -> {

                //validate the email field
                //check if all the field are valid and turn on the submit the button
                val emailError = validateEmail.execute(state.email).errorMessage
                val allFieldsValid: Boolean =
                    if (state.email != ""
                        && state.emailError == null
                    ) true else false
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
        Log.d("STATE", state.email)

        viewModelScope.launch { validationEventChannel.send(ValidationEvent.Success) }

    }

    sealed class ValidationEvent {
        data object Success : ValidationEvent()
    }

}

