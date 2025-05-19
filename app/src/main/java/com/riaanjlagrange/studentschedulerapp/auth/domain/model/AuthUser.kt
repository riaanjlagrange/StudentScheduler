package com.riaanjlagrange.studentschedulerapp.auth.domain.model

data class AuthUser(
    val uid: String = "",
    val name: String = "",
    val email: String = "",
    val studentId: String = "",
    val role: UserRole = UserRole.Student
)

// add lecturer & student user roles
enum class UserRole {
    Student,
    Lecturer
}