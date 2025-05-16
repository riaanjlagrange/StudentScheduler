package com.riaanjlagrange.studentschedulerapp.appointment.presentation.viewbookings

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import arrow.core.Either
import com.google.firebase.auth.FirebaseAuth
import com.riaanjlagrange.studentschedulerapp.appointment.data.repository.AppointmentRepositoryImpl
import com.riaanjlagrange.studentschedulerapp.appointment.domain.model.Appointment
import com.riaanjlagrange.studentschedulerapp.core.domain.model.FirestoreError
import kotlinx.coroutines.launch

class ViewBookingsViewModel : ViewModel() {

    private val repo = AppointmentRepositoryImpl()  // make sure this is properly constructed

    var state by mutableStateOf(ViewBookingsViewState())
        private set

    fun loadAppointments() {
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return

        state = state.copy(isLoading = true)

        viewModelScope.launch {
            val appointmentsResponse: Either<FirestoreError, List<Appointment>> = repo.getAppointments(userId)

            state = when (appointmentsResponse) {
                is Either.Right -> state.copy(
                    appointments = appointmentsResponse.value,
                    isLoading = false,
                    error = null
                )

                is Either.Left -> state.copy(
                    isLoading = false,
                    error = appointmentsResponse.value.error.message
                )
            }
        }
    }
}
