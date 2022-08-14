package com.polotika.androidunittesting

import com.google.common.truth.Truth.assertThat
import com.polotika.androidunittesting.utils.RegistrationUtils
import org.junit.Test

class RegistrationUtilsTest{

    @Test
    fun `register with empty username returns false`(){
        val result = RegistrationUtils.validateRegistrationInput("","abc123","abc123")
        assertThat(result).isFalse()
    }

    @Test
    fun `register with valid username and valid repeated password returns true`(){
        val result = RegistrationUtils.validateRegistrationInput("mohamed","abc123","abc123")
        assertThat(result).isTrue()
    }

    @Test
    fun `register with already taken username returns false`(){
        val result = RegistrationUtils.validateRegistrationInput("Ahmed","abc123","abc123")
        assertThat(result).isFalse()
    }

    @Test
    fun `incorrectly confirmed password returns false`() {
        val result = RegistrationUtils.validateRegistrationInput(
            "mohamed",
            "123456",
            "abcdefg"
        )
        assertThat(result).isFalse()
    }

    @Test
    fun `empty password returns false`() {
        val result = RegistrationUtils.validateRegistrationInput(
            "mohamed",
            "",
            ""
        )
        assertThat(result).isFalse()
    }

    @Test
    fun `less than 2 digit password returns false`() {
        val result = RegistrationUtils.validateRegistrationInput(
            "mohamed",
            "abcdefg5",
            "abcdefg5"
        )
        assertThat(result).isFalse()
    }
}