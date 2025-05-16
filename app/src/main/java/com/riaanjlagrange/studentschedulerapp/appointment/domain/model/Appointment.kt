package com.riaanjlagrange.studentschedulerapp.appointment.domain.model

import java.util.UUID

data class Appointment(
    val id: String = UUID.randomUUID().toString(),
    val userId: String = "",
    val date: String = "",
    val time: String = ""
)