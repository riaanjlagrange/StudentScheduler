package com.riaanjlagrange.studentschedulerapp.feedback.presentation.feedbackchatlist

import com.riaanjlagrange.studentschedulerapp.auth.domain.model.AuthUser

data class FeedbackChatListViewState(
    val users: List<AuthUser> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)
