package com.example.trustline.presentation.auth.reset_password.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.trustline.utils.ValidateConfirmPassword
import com.example.trustline.utils.ValidatePassword
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ResetPasswordViewModel(
    private val validatePassword: ValidatePassword = ValidatePassword(),
    private val validateConfirmPassword: ValidateConfirmPassword = ValidateConfirmPassword()
) : ViewModel() {
    private val _state = MutableStateFlow(ResetPasswordFormState())

    val resetPasswordState: StateFlow<ResetPasswordFormState> = _state
    private val _validationEventChannel = Channel<ValidationEvent>()

    val validationEvents = _validationEventChannel.receiveAsFlow()


    //    update password and validate


    private fun onPasswordChange(password: String) {
        val validatePasswordResult = validatePassword.execute(password)
        _state.update { passwordState ->
            passwordState.copy(
                password = password,
                passwordError = validatePasswordResult.errorMessage
            )
        }
    }

    //update confirm password and validate
    private fun onConfirmPasswordChange(confirmPassword: String) {
        val validatePasswordResult =
            validateConfirmPassword.execute(confirmPassword, resetPasswordState.value.password)
        _state.update { passwordState ->
            passwordState.copy(
                confirmPassword = confirmPassword,
                confirmPasswordError = validatePasswordResult.errorMessage
            )
        }


    }

    fun isPasswordMatch(): Boolean {
        return resetPasswordState.value.passwordError == null
                && resetPasswordState.value.confirmPasswordError == null
                && resetPasswordState.value.password != ""
                && resetPasswordState.value.confirmPassword != ""
    }


    fun onEvent(event: ResetPasswordFormEvent) {
        when (event) {
            is ResetPasswordFormEvent.PasswordChanged -> onPasswordChange(event.password)

            is ResetPasswordFormEvent.ConfirmPasswordChanged -> onConfirmPasswordChange(event.confirmPassword)

            ResetPasswordFormEvent.Submit -> {
                submitData()
            }
        }
    }


    private fun submitData() {
        viewModelScope.launch { _validationEventChannel.send(ValidationEvent.Success) }
    }

    sealed class ValidationEvent {
        data object Success : ValidationEvent()
    }

}

