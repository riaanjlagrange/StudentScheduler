package com.riaanjlagrange.studentschedulerapp.appointment.domain.model

data class FirebaseError(
    val error: ApiError,
    val t: Throwable? = null
)

enum class ApiError(val message: String) {
    PermissionError("Permission Denied"),
    ServiceUnavailableError("Service Currently Unavailable"),
    NotFoundError("Document Not Found"),
    TimeOutError("Request Timed Out"),
    NetworkError("No Network Connection"),
    UnknownError("Unknown Error"),
}
