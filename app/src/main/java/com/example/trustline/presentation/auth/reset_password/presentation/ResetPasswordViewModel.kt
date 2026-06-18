package com.example.trustline.presentation.auth.reset_password.presentation

import com.example.trustline.BuildConfig
import com.example.trustline.TrustlineApplication
import com.example.trustline.data.ApiResult
import com.example.trustline.data.AuthRepository
import com.example.trustline.data.ResetPasswordRequest
import com.example.trustline.utils.ValidateConfirmPassword
import com.example.trustline.utils.ValidatePassword
import com.example.trustline.utils.toTitleCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.UUID
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory

class ResetPasswordViewModel(
    private val validatePassword: ValidatePassword = ValidatePassword(),
    private val validateConfirmPassword: ValidateConfirmPassword = ValidateConfirmPassword(),
    private val authRepository: AuthRepository
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

            is ResetPasswordFormEvent.Submit -> {
                submitData(event.userId, event.otpId)
            }
        }
    }


    private fun submitData(userId: UUID, otpId: UUID) {
        _state.update { it.copy(isLoading = true, apiError = null) }
        viewModelScope.launch {
            val request = ResetPasswordRequest(
                newPassword = resetPasswordState.value.password,
                userId = userId,
                institutionId = UUID.fromString(BuildConfig.INSTITUTION_ID),
                otpId = otpId
            )

            val result = authRepository.resetPassword(request)
            when (result) {
                is ApiResult.Error -> {
                    _state.update {
                        it.copy(
                            apiError = result.message.toTitleCase(),
                            isLoading = false
                        )
                    }
                }

                is ApiResult.Success -> {
                    _state.update { it.copy(isLoading = false) }
                    _validationEventChannel.send(ValidationEvent.Success)
                }
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as TrustlineApplication)
                val authRepository = application.container.authRepository
                ResetPasswordViewModel(
                    authRepository = authRepository
                )
            }
        }
    }

    sealed class ValidationEvent {
        data object Success : ValidationEvent()
    }

}

