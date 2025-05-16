package com.riaanjlagrange.studentschedulerapp.core.data.mapper

import com.google.firebase.FirebaseApiNotAvailableException
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.firestore.FirebaseFirestoreException
import com.riaanjlagrange.studentschedulerapp.core.domain.model.FirestoreApiError
import com.riaanjlagrange.studentschedulerapp.core.domain.model.FirestoreError

fun Throwable.toFirestoreError(e: Exception): FirestoreError {
    val error = when(e) {
        is FirebaseFirestoreException -> {
            when (e.code) {
                FirebaseFirestoreException.Code.PERMISSION_DENIED -> FirestoreApiError.PermissionError
                FirebaseFirestoreException.Code.UNAVAILABLE -> FirestoreApiError.ServiceUnavailableError
                FirebaseFirestoreException.Code.NOT_FOUND -> FirestoreApiError.NotFoundError
                FirebaseFirestoreException.Code.DEADLINE_EXCEEDED -> FirestoreApiError.TimeOutError
                else -> FirestoreApiError.UnknownError
            }
        }
        is FirebaseNetworkException -> FirestoreApiError.NetworkError
        is FirebaseApiNotAvailableException -> FirestoreApiError.ServiceUnavailableError
        else -> FirestoreApiError.UnknownError
    }
    return FirestoreError(
        error = error,
        t = e
    )
}