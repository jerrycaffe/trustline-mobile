package com.example.trustline.presentation.auth.forgot_password.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.trustline.BuildConfig
import com.example.trustline.TrustlineApplication
import com.example.trustline.data.ApiResult
import com.example.trustline.data.AuthRepository
import com.example.trustline.data.ForgotPasswordRequest
import com.example.trustline.data.ForgotPasswordResponse
import com.example.trustline.utils.ValidateEmail
import com.example.trustline.utils.toTitleCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.util.UUID
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory

class ForgotPasswordViewModel(
    private val validateEmail: ValidateEmail = ValidateEmail(),
    private val authRepository: AuthRepository
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

        if (!emailResult.successful) {
            state = state.copy(
                emailError = emailResult.errorMessage
            )
            return
        }

        state = state.copy(
            emailError = null,
            isLoading = true,
            apiError = null
        )

        viewModelScope.launch {
            val request = ForgotPasswordRequest(
                email = state.email,
                institutionId = UUID.fromString(BuildConfig.INSTITUTION_ID)
            )

            val result = authRepository.forgotPassword(request)
            when (result) {
                is ApiResult.Error -> {
                    state = state.copy(
                        apiError = result.message.toTitleCase(),
                        isLoading = false
                    )
                }

                is ApiResult.Success -> {
                    val response = result.data
                    if (response != null) {
                        state = state.copy(isLoading = false)
                        validationEventChannel.send(ValidationEvent.Success(response))
                    } else {
                        state = state.copy(
                            apiError = "Successful but no data returned",
                            isLoading = false
                        )
                    }
                }
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as TrustlineApplication)
                val authRepository = application.container.authRepository
                ForgotPasswordViewModel(
                    authRepository = authRepository
                )
            }
        }
    }

    sealed class ValidationEvent {
        data class Success(val response: ForgotPasswordResponse) : ValidationEvent()
    }

}

