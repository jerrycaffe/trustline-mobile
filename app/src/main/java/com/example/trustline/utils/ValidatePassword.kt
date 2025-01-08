package com.example.trustline.utils

class ValidatePassword {
    fun execute(password: String): ValidationResult {
        if (password.length < 8) return ValidationResult(
            false, "Password must contain least 6 characters"
        )
        val containsLettersAndDigit =
            password.any { it.isDigit() } && password.any { it.isLetter() }
        if (!containsLettersAndDigit) return ValidationResult(
            false, "Password must contain least one digit and least one character"
        )
        return ValidationResult(true, errorMessage = null)
    }
}