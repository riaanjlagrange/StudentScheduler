package com.riaanjlagrange.studentschedulerapp.auth.presentation.lecturer.login

data class LoginState(
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val error: String? = null
)