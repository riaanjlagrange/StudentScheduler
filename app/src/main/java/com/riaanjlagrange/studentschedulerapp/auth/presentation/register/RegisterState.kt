package com.riaanjlagrange.studentschedulerapp.auth.presentation.register

data class RegisterState (
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val isLoading: Boolean = false,
    val error: String? = null
)