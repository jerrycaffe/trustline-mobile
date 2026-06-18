package com.example.trustline.presentation.gbvResources.presentation

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.trustline.BuildConfig
import com.example.trustline.TrustlineApplication
import com.example.trustline.data.ApiResult
import com.example.trustline.data.AuthRepository
import com.example.trustline.data.GbvResourceListResponse
import com.example.trustline.presentation.gbvResources.presentation.common.GbvResourceData
import com.example.trustline.utils.toTitleCase
import kotlinx.coroutines.launch

data class GbvResourcesState(
    val resources: List<GbvResourceData> = emptyList(),
    val isLoading: Boolean = false,
    val isLoadingMore: Boolean = false,
    val isLastPage: Boolean = false,
    val offset: Int = 0,
    val limit: Int = 20,
    val total: Int = 0,
    val error: String? = null,
    val hasLoadedOnce: Boolean = false
)

class GbvResourcesViewModel(
    private val authRepository: AuthRepository
) : ViewModel() {

    var state by mutableStateOf(GbvResourcesState())
        private set

    init {
        loadResources(reset = true)
    }

    fun loadNextPage() {
        if (state.isLoading || state.isLoadingMore || state.isLastPage) return
        loadResources(reset = false)
    }

    fun retryInitialLoad() {
        loadResources(reset = true)
    }

    private fun loadResources(reset: Boolean) {
        val currentOffset = if (reset) 0 else state.offset

        state = if (reset) {
            state.copy(isLoading = true, error = null)
        } else {
            state.copy(isLoadingMore = true, error = null)
        }

        viewModelScope.launch {
            when (
                val result = authRepository.getGbvResources(
                    institutionId = BuildConfig.INSTITUTION_ID,
                    offset = currentOffset,
                    limit = state.limit
                )
            ) {
                is ApiResult.Error -> {
                    state = state.copy(
                        isLoading = false,
                        isLoadingMore = false,
                        error = result.message.toTitleCase(),
                        hasLoadedOnce = true
                    )
                }

                is ApiResult.Success -> {
                    val payload = result.data ?: GbvResourceListResponse(
                        content = emptyList(),
                        count = 0,
                        total = 0,
                        last = true
                    )
                    Log.i(
                        "Loading GBV",
                        "Loaded ${payload.count} resources, total: ${payload.total}, last page: ${payload.last}"
                    )
                    val mappedResources = payload.content.orEmpty().map {
                        GbvResourceData(
                            id = it.id,
                            name = it.name,
                            incidentType = it.incidentType,
                            contents = it.contents,
                            fileUrl = it.fileUrl,
                            createdBy = it.createdBy,
                            createdAt = it.createdAt,
                            updatedAt = it.updatedAt
                        )
                    }

                    val mergedResources =
                        if (reset) mappedResources else state.resources + mappedResources
                    val newOffset = if (reset) payload.count else state.offset + payload.count

                    state = state.copy(
                        resources = mergedResources,
                        offset = newOffset,
                        total = payload.total,
                        isLastPage = payload.last,
                        isLoading = false,
                        isLoadingMore = false,
                        error = null,
                        hasLoadedOnce = true
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
                GbvResourcesViewModel(authRepository = authRepository)
            }
        }
    }
}



