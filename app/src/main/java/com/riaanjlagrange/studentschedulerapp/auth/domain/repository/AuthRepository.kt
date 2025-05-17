package com.riaanjlagrange.studentschedulerapp.auth.domain.repository

import arrow.core.Either
import com.riaanjlagrange.studentschedulerapp.auth.domain.model.AuthUser
import com.riaanjlagrange.studentschedulerapp.core.domain.model.FirebaseAuthError
import com.riaanjlagrange.studentschedulerapp.auth.domain.model.UserRole

interface AuthRepository {
    suspend fun login(email: String, password: String): Either<FirebaseAuthError, AuthUser>
    suspend fun register(name: String, email: String, password: String, confirmPassword: String, role: UserRole): Either<FirebaseAuthError, AuthUser>
}