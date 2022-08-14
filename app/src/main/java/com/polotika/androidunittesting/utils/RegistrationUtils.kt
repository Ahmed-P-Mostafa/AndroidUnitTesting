package com.polotika.androidunittesting.utils

object RegistrationUtils {
    private val existingUsers = listOf("Ahmed", "Mostafa")

    /**
     * the input is not valid if
     * username/password is empty
     * username is already taken
     * the password did not match the confirm password
     * the password not contains at least 2 digits*/
    fun validateRegistrationInput(
        username: String,
        password: String,
        confirmPassword: String
    ): Boolean {
        return when {
            username.isEmpty() || password.isEmpty() -> {
                false
            }
            existingUsers.contains(username) -> {
                false
            }
            password != confirmPassword ->{
                false
            }
            password.count { it.isDigit() } < 2->{
                false
            }
            else -> {
                true
            }
        }
    }
}