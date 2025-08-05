package com.example.trustline

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.trustline.data.RegisterUserRes

class MainViewModel : ViewModel() {
    var registeredUser by mutableStateOf<RegisterUserRes?>(null)
}