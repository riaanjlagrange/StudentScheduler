package com.riaanjlagrange.studentschedulerapp.appointment.presentation.viewbookings

import com.riaanjlagrange.studentschedulerapp.appointment.domain.model.Appointment
import com.riaanjlagrange.studentschedulerapp.auth.domain.model.AuthUser

data class ViewBookingsViewState(
    val appointments: List<Appointment> = emptyList(),
    val yourUser: AuthUser? = null,
    val yourUserIsLoading: Boolean = false,
    val yourUserError: String? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)