package com.riaanjlagrange.studentschedulerapp.auth.presentation.register

import com.riaanjlagrange.studentschedulerapp.auth.domain.model.UserRole

data class RegisterViewState(
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val role: UserRole = UserRole.Student,
    val isLoading: Boolean = false,
    val error: String? = null
)
