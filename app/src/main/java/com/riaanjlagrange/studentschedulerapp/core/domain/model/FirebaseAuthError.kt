package com.riaanjlagrange.studentschedulerapp.core.domain.model

data class FirebaseAuthError(
    val error: FirebaseAuthApiError,
    val t: Throwable? = null
)

enum class FirebaseAuthApiError(val message: String) {
    NotFoundError("User Not Found"),
    AuthenticationError("Incorrect Email or Password"),
    UserAlreadyExistsError("Email Already In Ise"),
    NetworkError("No Network Connection"),
    RateLimitError("Too Many Requests"),
    ServiceUnavailableError("Service Currently Unavailable"),
    UnknownError("Unknown Error"),
    PasswordsDoNotMatchError("Passwords Do Not Match"),
}
