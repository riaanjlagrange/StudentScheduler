package com.riaanjlagrange.studentschedulerapp.core.data.mapper

import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.*
import com.riaanjlagrange.studentschedulerapp.core.domain.model.FirebaseAuthApiError
import com.riaanjlagrange.studentschedulerapp.core.domain.model.FirebaseAuthError

fun Throwable.toFirebaseAuthError(): FirebaseAuthError {
    val error = when (this) {
        is FirebaseAuthInvalidUserException -> {
            FirebaseAuthApiError.NotFoundError
        }
        is FirebaseAuthInvalidCredentialsException -> {
            FirebaseAuthApiError.AuthenticationError
        }
        is FirebaseAuthUserCollisionException -> {
            FirebaseAuthApiError.UserAlreadyExistsError
        }
        is FirebaseNetworkException -> {
            FirebaseAuthApiError.NetworkError
        }
        is FirebaseAuthException -> {
            when (this.errorCode) {
                "ERROR_TOO_MANY_REQUESTS" -> FirebaseAuthApiError.RateLimitError
                "ERROR_OPERATION_NOT_ALLOWED" -> FirebaseAuthApiError.ServiceUnavailableError
                else -> FirebaseAuthApiError.UnknownError
            }
        }
        else -> FirebaseAuthApiError.UnknownError
    }

    return FirebaseAuthError(error = error, t = this)
}
