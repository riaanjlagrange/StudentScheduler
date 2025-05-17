package com.riaanjlagrange.studentschedulerapp.core.data.repository

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.riaanjlagrange.studentschedulerapp.auth.domain.model.AuthUser
import com.riaanjlagrange.studentschedulerapp.auth.domain.model.UserRole
import com.riaanjlagrange.studentschedulerapp.auth.domain.repository.UsersRepository
import com.riaanjlagrange.studentschedulerapp.core.domain.model.FirestoreError
import com.riaanjlagrange.studentschedulerapp.core.data.mapper.toFirestoreError
import com.riaanjlagrange.studentschedulerapp.core.domain.model.FirestoreApiError
import kotlinx.coroutines.tasks.await

class UsersRepositoryImp : UsersRepository {

    private val db = Firebase.firestore

    override suspend fun getAllStudents(): Either<FirestoreError, List<AuthUser>> {
        return try {
            val snapshot = db.collection("users")
                .whereEqualTo("role", UserRole.Student.name) // "Student"
                .get()
                .await()

            val users = snapshot.toObjects(AuthUser::class.java)
            users.right()
        } catch (e: Exception) {
            e.toFirestoreError(e).left()
        }
    }

    override suspend fun getAllLecturers(): Either<FirestoreError, List<AuthUser>> {
        return try {
            val snapshot = db.collection("users")
                .whereEqualTo("role", UserRole.Lecturer.name) // "Lecturer"
                .get()
                .await()

            val users = snapshot.toObjects(AuthUser::class.java)
            users.right()
        } catch (e: Exception) {
            e.toFirestoreError(e).left()
        }
    }

    override suspend fun getStudentById(studentId: String): Either<FirestoreError, AuthUser> {
        return try {
            val snapshot = db.collection("users")
                .whereEqualTo("uid", studentId)
                .whereEqualTo("role", UserRole.Student.name)
                .get()
                .await()

            val user = snapshot.toObjects(AuthUser::class.java).firstOrNull()
                ?: return FirestoreError(FirestoreApiError.NotFoundError).left()

            user.right()
        } catch (e: Exception) {
            e.toFirestoreError(e).left()
        }
    }
    override suspend fun getLecturerById(lecturerId: String): Either<FirestoreError, AuthUser> {
        return try {
            val snapshot = db.collection("users")
                .whereEqualTo("uid", lecturerId)
                .whereEqualTo("role", UserRole.Lecturer.name)
                .get()
                .await()

            val user = snapshot.toObjects(AuthUser::class.java).firstOrNull()
                ?: return FirestoreError(FirestoreApiError.NotFoundError).left()

            user.right()
        } catch (e: Exception) {
            e.toFirestoreError(e).left()
        }
    }
}
