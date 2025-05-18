package com.riaanjlagrange.studentschedulerapp.feedback.domain.model

import com.riaanjlagrange.studentschedulerapp.auth.domain.model.AuthUser
import java.util.UUID

data class FeedbackMessage(
    val id: String = UUID.randomUUID().toString(),
    val message: String = "",
    val category: FeedbackCategory = FeedbackCategory.General,
    val timestamp: Long = System.currentTimeMillis(),
    val lecturer: AuthUser? = null,
    val student: AuthUser? = null
)

enum class FeedbackCategory {
    General,
    Complaint,
    Suggestion,
    Question
}
