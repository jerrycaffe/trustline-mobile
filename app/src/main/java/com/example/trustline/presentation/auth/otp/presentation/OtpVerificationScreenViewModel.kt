package com.example.trustline.presentation.auth.otp.presentation

import android.os.CountDownTimer
import android.text.format.DateUtils
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.trustline.presentation.auth.forgot_password.presentation.ForgotPasswordFormEvent
import com.example.trustline.presentation.auth.forgot_password.presentation.ForgotPasswordFormState
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
    var timeLeft = mutableLongStateOf(DateUtils.formatElapsedTime(initialTotalTime).toLong())
    private val countDownInterval = 1000L

    val timerText: MutableState<String> = mutableStateOf(timeLeft.value.toString())

    val isPlaying = mutableStateOf(false)

    var state by mutableStateOf(ForgotPasswordFormState())

    private val validationEventChannel = Channel<ValidationEvent>()

    val validationEvents = validationEventChannel.receiveAsFlow()

    fun startCountDownTimer() = viewModelScope.launch {
        isPlaying.value = true
        countDownTimer = object : CountDownTimer(timeLeft.value, countDownInterval) {
            override fun onTick(currentTimeLeft: Long) {
                timerText.value = currentTimeLeft.toString()
                timeLeft.value = currentTimeLeft
            }

            override fun onFinish() {
                timerText.value = initialTotalTime.toString()
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
        timerText.value = initialTotalTime.toString()
        timeLeft.value = initialTotalTime
    }

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

