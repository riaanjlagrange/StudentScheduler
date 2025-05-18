package com.riaanjlagrange.studentschedulerapp.feedback.domain.model

import com.riaanjlagrange.studentschedulerapp.auth.domain.model.AuthUser
import com.riaanjlagrange.studentschedulerapp.auth.domain.model.UserRole
import java.util.UUID

data class FeedbackMessage(
    var id: String = UUID.randomUUID().toString(),
    var message: String = "",
    var category: FeedbackCategory = FeedbackCategory.General,
    val timestamp: Long = System.currentTimeMillis(),
    var from: UserRole = UserRole.Student,
    val participants: List<String> = emptyList(),
    val lecturer: AuthUser? = null,
    val student: AuthUser? = null
)

enum class FeedbackCategory {
    General,
    Complaint,
    Suggestion,
    Question
}
