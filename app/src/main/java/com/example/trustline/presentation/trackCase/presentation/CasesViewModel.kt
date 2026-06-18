package com.example.trustline.presentation.trackCase.presentation

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
import com.example.trustline.data.CaseApiModel
import com.example.trustline.data.PreferenceStorage
import kotlinx.coroutines.launch

enum class CaseStatusFilter { ALL, PENDING, RESOLVED }

data class CasesState(
    val allCases: List<CaseApiModel> = emptyList(),
    val isLoading: Boolean = false,
    val isLoadingMore: Boolean = false,
    val isLastPage: Boolean = false,
    val offset: Int = 0,
    val total: Int = 0,
    val error: String? = null,
    val hasLoadedOnce: Boolean = false,
    val searchQuery: String = "",
    val statusFilter: CaseStatusFilter = CaseStatusFilter.ALL
) {
    val filteredCases: List<CaseApiModel>
        get() {
            var result = allCases
            if (statusFilter != CaseStatusFilter.ALL) {
                val expectedStatus = statusFilter.name
                result = result.filter { it.status?.uppercase() == expectedStatus }
            }
            if (searchQuery.isNotBlank()) {
                val q = searchQuery.trim().lowercase()
                result = result.filter {
                    it.caseNumber?.lowercase()?.contains(q) == true ||
                        it.incidentType?.lowercase()?.contains(q) == true
                }
            }
            return result
        }
}

class CasesViewModel(
    private val authRepository: AuthRepository,
    private val preferenceStorage: PreferenceStorage
) : ViewModel() {

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = this[APPLICATION_KEY] as TrustlineApplication
                CasesViewModel(
                    authRepository = application.container.authRepository,
                    preferenceStorage = PreferenceStorage(application.applicationContext)
                )
            }
        }
    }

    var state by mutableStateOf(CasesState())
        private set

    init {
        loadCases(reset = true)
    }

    fun refresh() = loadCases(reset = true)

    fun loadNextPage() {
        if (state.isLoading || state.isLoadingMore || state.isLastPage) return
        loadCases(reset = false)
    }

    fun onSearchChanged(query: String) {
        state = state.copy(searchQuery = query)
    }

    fun onFilterChanged(filter: CaseStatusFilter) {
        state = state.copy(statusFilter = filter)
    }

    private fun loadCases(reset: Boolean) {
        val token = preferenceStorage.getAccessToken()
        if (token.isNullOrBlank()) {
            state = state.copy(
                error = "Please log in to view your cases",
                hasLoadedOnce = true,
                isLoading = false,
                isLoadingMore = false
            )
            return
        }

        state = if (reset) {
            state.copy(isLoading = true, error = null)
        } else {
            state.copy(isLoadingMore = true, error = null)
        }

        viewModelScope.launch {
            when (val result = authRepository.getCases(token, offset = 0, limit = 20)) {
                is com.example.trustline.data.ApiResult.Error -> {
                    state = state.copy(
                        isLoading = false,
                        isLoadingMore = false,
                        error = result.message,
                        hasLoadedOnce = true
                    )
                }

                is com.example.trustline.data.ApiResult.Success -> {
                    val payload = result.data
                    val incoming = payload?.content.orEmpty()
                    val merged = if (reset) incoming else state.allCases + incoming

                    state = state.copy(
                        allCases = merged,
                        offset = merged.size,
                        total = payload?.total ?: merged.size,
                        isLastPage = payload?.last ?: true,
                        isLoading = false,
                        isLoadingMore = false,
                        error = null,
                        hasLoadedOnce = true
                    )
                }
            }
        }
    }
}
