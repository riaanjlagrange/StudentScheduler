package com.riaanjlagrange.studentschedulerapp.appointment.domain.model

import com.riaanjlagrange.studentschedulerapp.auth.domain.model.AuthUser
import java.util.UUID

data class Appointment(
    val id: String = UUID.randomUUID().toString(),
    val userId: String = "",
    val date: String = "",
    val time: String = "",
    val lecturer: AuthUser? = null,
    val student: AuthUser? = null
)

fun Appointment.getCounterPartyName(): String? {
    return lecturer?.name ?: student?.name
}

fun Appointment.getCounterPartyRole(): String {
    return when {
        lecturer != null -> "Lecturer"
        student != null -> "Student"
        else -> "Unknown"
    }
}