package com.riaanjlagrange.studentschedulerapp.appointment.domain.repository

import arrow.core.Either
import com.riaanjlagrange.studentschedulerapp.appointment.domain.model.Appointment
import com.riaanjlagrange.studentschedulerapp.core.domain.model.FirestoreError

interface AppointmentRepository {
    suspend fun getAppointments(userId: String): Either<FirestoreError, List<Appointment>>
    suspend fun bookAppointment(appt: Appointment): Either<FirestoreError, String>
}