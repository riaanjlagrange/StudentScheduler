package com.riaanjlagrange.studentschedulerapp.appointment.presentation.viewbookings

import com.riaanjlagrange.studentschedulerapp.appointment.domain.model.Appointment

data class ViewBookingsViewState(
    val appointments: List<Appointment> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)