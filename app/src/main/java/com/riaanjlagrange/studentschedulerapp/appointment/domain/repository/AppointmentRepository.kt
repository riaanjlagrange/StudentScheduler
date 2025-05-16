package com.riaanjlagrange.studentschedulerapp.appointment.domain.repository

import arrow.core.Either
import com.riaanjlagrange.studentschedulerapp.appointment.domain.model.Appointment
import com.riaanjlagrange.studentschedulerapp.appointment.domain.model.FirebaseError

interface AppointmentRepository {
    suspend fun getAppointments(userId: String): Either<FirebaseError, List<Appointment>>
    suspend fun bookAppointment(appt: Appointment): Either<FirebaseError, String>
}