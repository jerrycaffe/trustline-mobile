package com.example.trustline.utils

data class ValidationResult(
    val successful: Boolean,
    val errorMessage: String? = null
)
