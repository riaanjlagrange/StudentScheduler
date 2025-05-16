package com.riaanjlagrange.studentschedulerapp.appointment.data.repository

import arrow.core.Either
import com.google.firebase.Firebase
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.firestore
import com.riaanjlagrange.studentschedulerapp.core.data.mapper.toFirestoreError
import com.riaanjlagrange.studentschedulerapp.appointment.domain.model.Appointment
import com.riaanjlagrange.studentschedulerapp.core.domain.model.FirestoreError
import com.riaanjlagrange.studentschedulerapp.appointment.domain.repository.AppointmentRepository
import kotlinx.coroutines.tasks.await

class AppointmentRepositoryImpl(
): AppointmentRepository {
    // get db from firestore
    val db = Firebase.firestore

    // set booking document in appointments collection
    // set onResult lambda to be used in BookingViewModel
    override suspend fun bookAppointment(appt: Appointment): Either<FirestoreError, String> {
        return try {
            db.collection("appointments")
                .document(appt.id)
                .set(appt)
                .await() // suspends until complete
            Either.Right("Appointment Booked!")
        } catch (e: Exception) {
            Either.Left(e.toFirestoreError(e))
        }
    }

    // fetch appointment for user
    override suspend fun getAppointments(userId: String): Either<FirestoreError, List<Appointment>> {
        return try {
            val snapshot: QuerySnapshot = db.collection("appointments")
                .whereEqualTo("userId", userId)
                .get()
                .await()  // suspends until task completes

            val appointments = snapshot.documents.mapNotNull { it.toObject(Appointment::class.java) }
            Either.Right(appointments)
        } catch (e: Exception) {
            Either.Left(e.toFirestoreError(e))
        }
    }
}