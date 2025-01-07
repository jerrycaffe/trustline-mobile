package com.example.trustline.domain.use_cases

import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class ValidateEmailTest {
    private lateinit var validatePassword: ValidatePassword

    @Before
    fun setUp() {
        validatePassword = ValidatePassword()
    }

    @Test
    fun `Password is letter-only, returns error`() {
        val result = validatePassword.execute("adedayo")
        assertEquals(result.successful, false)
    }
}