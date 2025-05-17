package com.riaanjlagrange.studentschedulerapp.auth.domain.repository

import arrow.core.Either
import com.riaanjlagrange.studentschedulerapp.auth.domain.model.AuthUser
import com.riaanjlagrange.studentschedulerapp.core.domain.model.FirestoreError

interface UsersRepository {
    suspend fun getAllStudents(): Either<FirestoreError, List<AuthUser>>
    suspend fun getStudentById(studentId: String): Either<FirestoreError, AuthUser>
    suspend fun getAllLecturers(): Either<FirestoreError, List<AuthUser>>
    suspend fun getLecturerById(lecturerId: String): Either<FirestoreError, AuthUser>
}
