package com.riaanjlagrange.studentschedulerapp.appointment.presentation.booking

import com.riaanjlagrange.studentschedulerapp.appointment.domain.model.Appointment
import com.riaanjlagrange.studentschedulerapp.auth.domain.model.AuthUser

data class BookingViewState(
    val appointment: Appointment = Appointment(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val userOptions: List<AuthUser> = emptyList(),
    val selectedUser: AuthUser? = null
)