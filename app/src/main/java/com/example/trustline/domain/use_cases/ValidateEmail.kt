package com.example.trustline.domain.use_cases

import android.util.Patterns

class ValidateEmail {
    //can access database or datasource
    fun execute(email: String): ValidationResult {
        if (email.isBlank()) return ValidationResult(false, "The email cannot be blank")

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) return ValidationResult(
            false,
            "This is not a valid email"
        )
        return ValidationResult(true)
    }
}