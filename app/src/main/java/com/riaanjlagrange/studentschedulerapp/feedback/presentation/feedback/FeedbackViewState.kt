package com.riaanjlagrange.studentschedulerapp.feedback.presentation.feedback

import com.riaanjlagrange.studentschedulerapp.auth.domain.model.AuthUser
import com.riaanjlagrange.studentschedulerapp.feedback.domain.model.FeedbackMessage

data class FeedbackViewState(
    val message: FeedbackMessage = FeedbackMessage(),
    val messageIsLoading: Boolean = false,
    val messageError: String? = null,

    val messages: List<FeedbackMessage> = emptyList<FeedbackMessage>(),
    val error: String? = null,
    val isLoading: Boolean = false,

    val yourUser: AuthUser? = null,
    val yourUserIsLoading: Boolean = false,
    val yourUserError: String? = null,

    val selectedUser: AuthUser? = null,
    val selectedUserIsLoading: Boolean = false,
    val selectedUserError: String? = null
)
