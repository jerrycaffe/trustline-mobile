package com.example.trustline.utils

class ValidateConfirmPassword {
    fun execute(password: String, confirmPassword: String): ValidationResult {
        return if (password != confirmPassword) ValidationResult(false, "Password mismatch!")
        else ValidationResult(true, "")
    }
}