package com.riaanjlagrange.studentschedulerapp.appointment.presentation.booking

import com.riaanjlagrange.studentschedulerapp.appointment.domain.model.Appointment

data class BookingViewState (
    val appointment: Appointment = Appointment(),
    val isLoading: Boolean = false,
    val error: String? = null
)