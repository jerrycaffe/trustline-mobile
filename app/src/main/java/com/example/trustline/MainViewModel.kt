package com.example.trustline

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.trustline.presentation.common.OtpValidationContent

class MainViewModel : ViewModel() {
    var otpValidationContent by mutableStateOf<OtpValidationContent?>(null)
}