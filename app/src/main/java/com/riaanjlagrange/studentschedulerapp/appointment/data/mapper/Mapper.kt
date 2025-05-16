package com.riaanjlagrange.studentschedulerapp.appointment.data.mapper

import com.google.firebase.FirebaseApiNotAvailableException
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.firestore.FirebaseFirestoreException
import com.riaanjlagrange.studentschedulerapp.appointment.domain.model.ApiError
import com.riaanjlagrange.studentschedulerapp.appointment.domain.model.FirebaseError

fun Throwable.toFireBaseError(e: Exception): FirebaseError {
    val error = when(e) {
        is FirebaseFirestoreException -> {
            when (e.code) {
                FirebaseFirestoreException.Code.PERMISSION_DENIED -> ApiError.PermissionError
                FirebaseFirestoreException.Code.UNAVAILABLE -> ApiError.ServiceUnavailableError
                FirebaseFirestoreException.Code.NOT_FOUND -> ApiError.NotFoundError
                FirebaseFirestoreException.Code.DEADLINE_EXCEEDED -> ApiError.TimeOutError
                else -> ApiError.UnknownError
            }
        }
        is FirebaseNetworkException -> ApiError.NetworkError
        is FirebaseApiNotAvailableException -> ApiError.ServiceUnavailableError
        else -> ApiError.UnknownError
    }
    return FirebaseError(
        error = error,
        t = e
    )
}