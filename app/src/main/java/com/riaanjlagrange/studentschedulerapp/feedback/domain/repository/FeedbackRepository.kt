package com.riaanjlagrange.studentschedulerapp.feedback.domain.repository

import arrow.core.Either
import com.riaanjlagrange.studentschedulerapp.core.domain.model.FirestoreError
import com.riaanjlagrange.studentschedulerapp.feedback.domain.model.FeedbackMessage

interface FeedbackRepository {
    suspend fun sendFeedback(message: FeedbackMessage): Either<FirestoreError, Unit>
    suspend fun getFeedbackMessages(userId: String, withUserId: String): Either<FirestoreError, List<FeedbackMessage>>
}