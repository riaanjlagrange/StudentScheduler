package com.riaanjlagrange.studentschedulerapp.appointment.presentation.booking

import com.riaanjlagrange.studentschedulerapp.appointment.domain.model.Appointment
import com.riaanjlagrange.studentschedulerapp.auth.domain.model.AuthUser

data class BookingViewState(
    val appointment: Appointment = Appointment(),

    val yourUser: AuthUser? = null,
    val yourUserIsLoading: Boolean = false,
    val yourUserError: String? = null,

    val userOptions: List<AuthUser> = emptyList(),
    val userOptionsIsLoading: Boolean = false,
    val userOptionsError: String? = null,

    val isLoading: Boolean = false,
    val error: String? = null,
    val selectedUser: AuthUser? = null,
)