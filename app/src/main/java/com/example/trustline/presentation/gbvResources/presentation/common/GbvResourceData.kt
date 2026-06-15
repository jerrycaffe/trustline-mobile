package com.example.trustline.presentation.gbvResources.presentation.common

import java.time.LocalDateTime
import java.util.UUID

data class GbvResourceData(
    val id: UUID,
    val name: String,
    val incidentType: String,
    val contents: String,
    val fileUrl: String?,
    val createdBy: String,
    val createdAt: LocalDateTime?,
    val updatedAt: LocalDateTime?
)