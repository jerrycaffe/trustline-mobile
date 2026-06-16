package com.example.trustline

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.trustline.data.CaseApiModel
import com.example.trustline.presentation.common.OtpValidationContent
import com.example.trustline.presentation.gbvResources.presentation.common.GbvResourceData

class MainViewModel : ViewModel() {
    var otpValidationContent by mutableStateOf<OtpValidationContent?>(null)
    var isUserAuthenticated by mutableStateOf(false)
    var pendingPostAuthRoute by mutableStateOf<String?>(null)

    var gbvResourceData by mutableStateOf<GbvResourceData?>(null)
    var selectedCaseForTracking by mutableStateOf<CaseApiModel?>(null)

    fun rememberPendingPostAuthRoute(route: String) {
        pendingPostAuthRoute = route
    }

    fun rememberGbvResourceState(gbvResourceData: GbvResourceData) {
        this.gbvResourceData = gbvResourceData
    }

    fun rememberSelectedCaseForTracking(case: CaseApiModel) {
        selectedCaseForTracking = case
    }

    fun consumePendingPostAuthRoute(): String? {
        val route = pendingPostAuthRoute
        pendingPostAuthRoute = null
        return route
    }

    fun consumeGbvResourceState(): GbvResourceData? {
        return gbvResourceData
    }

    fun consumeSelectedCaseForTracking(): CaseApiModel? {
        return selectedCaseForTracking
    }
}