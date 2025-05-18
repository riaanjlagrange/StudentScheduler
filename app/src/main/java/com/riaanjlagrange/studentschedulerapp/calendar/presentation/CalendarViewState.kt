package com.riaanjlagrange.studentschedulerapp.calendar.presentation

import com.riaanjlagrange.studentschedulerapp.appointment.domain.model.Appointment
import com.riaanjlagrange.studentschedulerapp.auth.domain.model.AuthUser

data class CalendarViewState(
    val appointments: List<Appointment> = emptyList(),
    val yourUser: AuthUser? = null,
    val userIsLoading: Boolean = false,
    val appointmentsIsLoading: Boolean = false,
    val appointmentsError: String? = null,
    val userError: String? = null
)