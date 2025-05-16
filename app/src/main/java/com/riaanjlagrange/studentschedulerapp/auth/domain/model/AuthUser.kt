package com.riaanjlagrange.studentschedulerapp.auth.domain.model

data class AuthUser(
    val uid: String = "",
    val email: String = "",
    val role: UserRole = UserRole.Student
)

// add lecturer & student user roles
enum class UserRole {
    Student,
    Lecturer
}