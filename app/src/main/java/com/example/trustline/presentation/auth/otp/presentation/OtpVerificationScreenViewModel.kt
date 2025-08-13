package com.example.trustline.presentation.auth.otp.presentation

import android.os.CountDownTimer
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.trustline.presentation.auth.forgot_password.presentation.ForgotPasswordFormState
import com.example.trustline.utils.TimeFormatExt.timeFormat
import com.example.trustline.utils.ValidatePhoneNumber
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class OtpVerificationScreenViewModel(
    private val validateEmail: ValidatePhoneNumber = ValidatePhoneNumber()
) : ViewModel() {
    private var countDownTimer: CountDownTimer? = null
    private val minutes = TimeUnit.MINUTES.toMillis(3)
    private val seconds = TimeUnit.SECONDS.toMillis(59)

    val initialTotalTime: Long = minutes + seconds
    var timeLeft = mutableLongStateOf(initialTotalTime)
    private val countDownInterval = 1000L

    val timerText = mutableStateOf(timeLeft.value.timeFormat())

    val isPlaying = mutableStateOf(false)

    var state by mutableStateOf(ForgotPasswordFormState())

    private val validationEventChannel = Channel<ValidationEvent>()

    val validationEvents = validationEventChannel.receiveAsFlow()

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
        submitData()
    }

    private fun submitData() {
        viewModelScope.launch { validationEventChannel.send(ValidationEvent.Success) }
    }

    sealed class ValidationEvent {
        data object Success : ValidationEvent()
    }

}

