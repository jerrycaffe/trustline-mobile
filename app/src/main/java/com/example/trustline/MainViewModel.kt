package com.example.trustline

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.trustline.presentation.common.OtpValidationContent
import com.example.trustline.presentation.gbvResources.presentation.common.GbvResourceData

class MainViewModel : ViewModel() {
    var otpValidationContent by mutableStateOf<OtpValidationContent?>(null)
    var isUserAuthenticated by mutableStateOf(false)
    var pendingPostAuthRoute by mutableStateOf<String?>(null)

    var gbvResourceData by mutableStateOf<GbvResourceData?>(null)
    fun rememberPendingPostAuthRoute(route: String) {
        pendingPostAuthRoute = route
    }

    fun rememberGbvResourceState(gbvResourceData: GbvResourceData) {
        this.gbvResourceData = gbvResourceData
    }

    fun consumePendingPostAuthRoute(): String? {
        val route = pendingPostAuthRoute
        pendingPostAuthRoute = null
        return route
    }

    fun consumeGbvResourceState(): GbvResourceData? {
        return gbvResourceData
    }
}