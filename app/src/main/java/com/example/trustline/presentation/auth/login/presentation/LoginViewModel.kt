package com.example.trustline.presentation.auth.login.presentation

import com.example.trustline.BuildConfig
import com.example.trustline.TrustlineApplication
import com.example.trustline.data.ApiResult
import com.example.trustline.data.AuthRepository
import com.example.trustline.data.LoginRequest
import com.example.trustline.data.LoginResponse
import com.example.trustline.data.PreferenceStorage
import com.example.trustline.utils.ValidateEmail
import com.example.trustline.utils.ValidatePassword
import com.example.trustline.utils.toTitleCase
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.util.UUID
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory

class LoginViewModel(
    private val validateEmail: ValidateEmail = ValidateEmail(),
    private val validatePassword: ValidatePassword = ValidatePassword(),
    private val authRepository: AuthRepository,
    private val preferenceStorage: PreferenceStorage
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
            return
        }

        state = state.copy(
            emailError = null,
            passwordError = null,
            isLoading = true,
            apiError = null
        )

        viewModelScope.launch {
            val request = LoginRequest(
                userName = state.email,
                password = state.password,
                institutionId = UUID.fromString(BuildConfig.INSTITUTION_ID)
            )

            val result = authRepository.loginUser(request)
            when (result) {
                is ApiResult.Error -> {
                    state = state.copy(
                        apiError = result.message.toTitleCase(),
                        isLoading = false
                    )
                }

                is ApiResult.Success -> {
                    val loginData = result.data
                    if (loginData != null) {
                        preferenceStorage.saveAccessToken(loginData.accessToken)
                        preferenceStorage.saveUserDetails(loginData.userDetails)
                        
                        state = state.copy(isLoading = false)
                        validationEventChannel.send(ValidationEvent.Success(loginData))
                    } else {
                        state = state.copy(
                            apiError = "Login successful but no data returned",
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
                val preferenceStorage = PreferenceStorage(application.applicationContext)
                LoginViewModel(
                    authRepository = authRepository,
                    preferenceStorage = preferenceStorage
                )
            }
        }
    }

    sealed class ValidationEvent {
        data class Success(val loginResponse: LoginResponse) : ValidationEvent()
    }
}

