package com.example.trustline.presentation.auth.otp.presentation

import android.os.CountDownTimer
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.trustline.TrustlineApplication
import com.example.trustline.data.ApiResult
import com.example.trustline.data.AuthRepository
import com.example.trustline.data.OtpValidationRequest
import com.example.trustline.utils.TimeFormatExt.timeFormat
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.util.UUID
import java.util.concurrent.TimeUnit

class OtpVerificationScreenViewModel(
    private val authRepository: AuthRepository
) : ViewModel() {
    private var countDownTimer: CountDownTimer? = null
    private val minutes = TimeUnit.MINUTES.toMillis(3)
    private val seconds = TimeUnit.SECONDS.toMillis(59)

    val initialTotalTime: Long = minutes + seconds
    var timeLeft = mutableLongStateOf(initialTotalTime)
    private val countDownInterval = 1000L

    val timerText = mutableStateOf(timeLeft.value.timeFormat())

    val isPlaying = mutableStateOf(false)

    var state by mutableStateOf(OtpFormState())

    private val validationEventChannel = Channel<ValidationEvent>()

    val validationEvents = validationEventChannel.receiveAsFlow()

    fun handleOtpChange(otpValue: String) {
        state = state.copy(
            otpValue = otpValue,
            isAllFieldValid = otpValue.length == state.otpCount
        )

    }

    fun startCountDownTimer() = viewModelScope.launch {
        isPlaying.value = true
        countDownTimer = object : CountDownTimer(timeLeft.value, countDownInterval) {
            override fun onTick(currentTimeLeft: Long) {
                timerText.value = currentTimeLeft.timeFormat()
                timeLeft.value = currentTimeLeft
            }

            override fun onFinish() {
                timerText.value = initialTotalTime.timeFormat()
                isPlaying.value = false
            }
        }.start()
    }

    fun stopCountDownTimer() = viewModelScope.launch {
        isPlaying.value = false
        countDownTimer?.cancel()
    }

    fun resetCountDownTimer() = viewModelScope.launch {
        isPlaying.value = false
        countDownTimer?.cancel()
        timerText.value = initialTotalTime.timeFormat()
        timeLeft.value = initialTotalTime
    }

    fun onEvent(event: OtpFormEvent) {
        when (event) {
            is OtpFormEvent.OtpChanged -> handleOtpChange(event.otpValue)
            is OtpFormEvent.Submit -> submitData(state.otpValue, event.userId)
        }
    }

    fun submitData(otpValue: String, userId: UUID) {
        viewModelScope.launch {
            val request = OtpValidationRequest(otpValue, userId)
            val result = authRepository.otpValidation(request)
            when (result) {
                is ApiResult.Error -> state = state.copy(apiError = result.message)

                is ApiResult.Success -> validationEventChannel.send(ValidationEvent.Success(true))
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as TrustlineApplication)
                val authRepository = application.container.authRepository
                OtpVerificationScreenViewModel(authRepository = authRepository)
            }
        }
    }

    sealed class ValidationEvent {
        data class Success(val isSuccess: Boolean) : ValidationEvent()
    }
}






