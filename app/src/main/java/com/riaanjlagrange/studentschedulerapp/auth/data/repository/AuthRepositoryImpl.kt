package com.riaanjlagrange.studentschedulerapp.auth.data.repository

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.riaanjlagrange.studentschedulerapp.auth.domain.model.AuthUser
import com.riaanjlagrange.studentschedulerapp.auth.domain.model.UserRole
import com.riaanjlagrange.studentschedulerapp.auth.domain.repository.AuthRepository
import com.riaanjlagrange.studentschedulerapp.core.domain.model.FirebaseAuthError
import com.riaanjlagrange.studentschedulerapp.core.data.mapper.toFirebaseAuthError
import com.riaanjlagrange.studentschedulerapp.core.domain.model.FirebaseAuthApiError
import kotlinx.coroutines.tasks.await

class AuthRepositoryImpl : AuthRepository {

    private val auth = FirebaseAuth.getInstance()
    private val firestore = Firebase.firestore

    override suspend fun login(email: String, password: String): Either<FirebaseAuthError, AuthUser> {
        return try {
            auth.signInWithEmailAndPassword(email, password).await()

            val uid = auth.currentUser?.uid
                ?: return FirebaseAuthError(error = FirebaseAuthApiError.UnknownError).left()

            val snapshot = firestore.collection("users").document(uid).get().await()

            val user = snapshot.toObject(AuthUser::class.java)
                ?: return FirebaseAuthError(error = FirebaseAuthApiError.NotFoundError).left()

            user.right()

        } catch (e: Exception) {
            e.toFirebaseAuthError().left()
        }
    }

    override suspend fun register(
        email: String,
        password: String,
        confirmPassword: String,
        role: UserRole
    ): Either<FirebaseAuthError, AuthUser> {
        if (password != confirmPassword) {
            return FirebaseAuthError(FirebaseAuthApiError.PasswordsDoNotMatchError).left()
        }

        return try {
            val result = auth.createUserWithEmailAndPassword(email, password).await()
            val uid = result.user?.uid
                ?: return FirebaseAuthError(FirebaseAuthApiError.UnknownError).left()

            val user = AuthUser(
                uid = uid,
                email = email,
                role = role
            )

            firestore.collection("users").document(uid).set(user).await()

            user.right()
        } catch (e: Exception) {
            e.toFirebaseAuthError().left()
        }
    }
}
