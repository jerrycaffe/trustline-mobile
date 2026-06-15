package com.example.trustline.presentation.report.presentation.presentation

import androidx.compose.runtime.getValue
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
import com.example.trustline.data.IncidentType
import com.example.trustline.data.PreferenceStorage
import com.example.trustline.utils.toTitleCase
import kotlinx.coroutines.launch

data class AuthReportCaseState(
    val incidentTypes: List<IncidentType> = emptyList(),
    val incidentTypeError: String? = null,
    val isLoadingIncidentTypes: Boolean = false
)

class AuthReportCaseViewModel(
    private val authRepository: AuthRepository,
    private val preferenceStorage: PreferenceStorage
) : ViewModel() {
    var state by mutableStateOf(AuthReportCaseState())
        private set

    init {
        loadIncidentTypes()
    }

    fun loadIncidentTypes() {
        val token = preferenceStorage.getAccessToken()
        if (token.isNullOrBlank()) {
            state = state.copy(
                incidentTypeError = "Authentication token not found"
            )
            return
        }

        state = state.copy(isLoadingIncidentTypes = true, incidentTypeError = null)
        viewModelScope.launch {
            when (val result = authRepository.getIncidentTypes(token)) {
                is ApiResult.Error -> {
                    state = state.copy(
                        isLoadingIncidentTypes = false,
                        incidentTypeError = result.message.toTitleCase()
                    )
                }

                is ApiResult.Success -> {
                    state = state.copy(
                        isLoadingIncidentTypes = false,
                        incidentTypes = result.data.orEmpty(),
                        incidentTypeError = null
                    )
                }
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = this[APPLICATION_KEY] as TrustlineApplication
                val authRepository = application.container.authRepository
                val preferenceStorage = PreferenceStorage(application.applicationContext)
                AuthReportCaseViewModel(
                    authRepository = authRepository,
                    preferenceStorage = preferenceStorage
                )
            }
        }
    }
}

