package com.riaanjlagrange.studentschedulerapp.core.domain.model

data class FirestoreError(
    val error: FirestoreApiError,
    val t: Throwable? = null
)

enum class FirestoreApiError(val message: String) {
    PermissionError("Permission Denied"),
    ServiceUnavailableError("Service Currently Unavailable"),
    NotFoundError("Document Not Found"),
    TimeOutError("Request Timed Out"),
    NetworkError("No Network Connection"),
    UnknownError("Unknown Error"),
}
