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