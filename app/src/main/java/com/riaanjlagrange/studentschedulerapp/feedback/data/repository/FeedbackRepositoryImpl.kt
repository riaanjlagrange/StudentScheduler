
import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.riaanjlagrange.studentschedulerapp.core.domain.model.FirestoreError
import com.riaanjlagrange.studentschedulerapp.core.data.mapper.toFirestoreError
import com.riaanjlagrange.studentschedulerapp.feedback.domain.model.FeedbackMessage
import com.riaanjlagrange.studentschedulerapp.feedback.domain.repository.FeedbackRepository
import kotlinx.coroutines.tasks.await

class FeedbackRepositoryImpl : FeedbackRepository {
    private val db = Firebase.firestore

    override suspend fun sendFeedback(message: FeedbackMessage): Either<FirestoreError, Unit> {
        return try {
            db.collection("feedback").document(message.id).set(message).await()
            Unit.right()
        } catch (e: Exception) {
            e.toFirestoreError(e).left()
        }
    }

    override suspend fun getFeedbackMessages(
        userId: String,
        withUserId: String
    ): Either<FirestoreError, List<FeedbackMessage>> {
        return try {
            val snapshot = db.collection("feedback")
                .whereIn("student.uid", listOf(userId, withUserId))
                .whereIn("lecturer.uid", listOf(userId, withUserId))
                .orderBy("timestamp")
                .get()
                .await()

            val messages = snapshot.toObjects(FeedbackMessage::class.java)
            messages.right()
        } catch (e: Exception) {
            e.toFirestoreError(e).left()
        }
    }
}