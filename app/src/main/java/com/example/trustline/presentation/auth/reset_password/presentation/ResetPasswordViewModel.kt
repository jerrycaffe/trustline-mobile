package com.example.trustline.presentation.auth.reset_password.presentation

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.trustline.utils.ValidateConfirmPassword
import com.example.trustline.utils.ValidatePassword
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class ResetPasswordViewModel(
    private val validatePassword: ValidatePassword = ValidatePassword(),
    private val validateConfirmPassword: ValidateConfirmPassword = ValidateConfirmPassword()
) : ViewModel() {
    var state by mutableStateOf(ResetPasswordFormState())

    private val validationEventChannel = Channel<ValidationEvent>()

    val validationEvents = validationEventChannel.receiveAsFlow()

    fun onEvent(event: ResetPasswordFormEvent) {
        when (event) {


            is ResetPasswordFormEvent.PasswordChanged -> {
                val passwordError = validatePassword.execute(state.password).errorMessage
                val allFieldsValid: Boolean =
                    if (state.password != ""
                        && state.passwordError == null
                        && state.confirmPassword != ""
                    ) true else false
                state =
                    state.copy(
                        password = event.password,
                        passwordError = passwordError
                    )
            }

            is ResetPasswordFormEvent.ConfirmPasswordChanged -> {
                val confirmPasswordError = validateConfirmPassword.execute(
                    state.password,
                    state.confirmPassword
                ).errorMessage
                val allFieldsValid: Boolean =
                    if (state.password != ""
                        && state.passwordError == null
                        && state.confirmPassword != ""
                    ) true else false
                state =
                    state.copy(
                        password = event.confirmPassword,
                        passwordError = confirmPasswordError
                    )
            }

            ResetPasswordFormEvent.Submit -> {
                submitData()
            }
        }
    }

    private fun submitData() {
        val passwordResult = validatePassword.execute(state.password)
        val confirmPasswordResult =
            validateConfirmPassword.execute(state.password, state.confirmPassword)

        val hasError = listOf(
            passwordResult, confirmPasswordResult
        ).any { !it.successful }

        if (hasError) {
            state = state.copy(

                passwordError = passwordResult.errorMessage,
                confirmPasswordError = confirmPasswordResult.errorMessage
            )

        } else {
            state = state.copy(
                confirmPasswordError = null,
                passwordError = null
            )
        }
        Log.d("STATE", state.confirmPassword)
        Log.d("STATE", state.password)

        viewModelScope.launch { validationEventChannel.send(ValidationEvent.Success) }

    }

    sealed class ValidationEvent {
        data object Success : ValidationEvent()
    }

}

