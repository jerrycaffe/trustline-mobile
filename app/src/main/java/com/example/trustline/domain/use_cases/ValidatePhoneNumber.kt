package com.example.trustline.domain.use_cases

class ValidatePhoneNumber {
    fun execute(phoneNumber: String): ValidationResult {
        if (phoneNumber.length < 10) return ValidationResult(
            false, "Phone number must be 11 digit long"
        )

        if (!phoneNumberValid(phoneNumber)) return ValidationResult(
            false, "Phone number must be of patter 08081234567 or +23480273625"
        )
        return ValidationResult(true)
    }

    private fun phoneNumberValid(phoneNumber: String): Boolean {
        val regex = "^\\+?[0-9]{10,15}$".toRegex()
        return regex.matches(phoneNumber)
    }
}