package com.example.trustline.presentation.common

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.trustline.data.RegisterUserRes

class SharedUserViewModel : ViewModel() {
    var user by mutableStateOf<RegisterUserRes?>(null)
}