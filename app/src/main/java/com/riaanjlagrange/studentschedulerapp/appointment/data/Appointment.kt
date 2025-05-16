package com.riaanjlagrange.studentschedulerapp.appointment.data

import java.util.UUID

data class Appointment(
    val id: String = UUID.randomUUID().toString(),
    val userId: String = "",
    val date: String = "",
    val time: String = ""
)
