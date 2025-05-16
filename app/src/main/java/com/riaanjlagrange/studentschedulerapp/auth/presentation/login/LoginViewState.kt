package com.riaanjlagrange.studentschedulerapp.auth.presentation.login

data class LoginViewState(
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val error: String? = null
)