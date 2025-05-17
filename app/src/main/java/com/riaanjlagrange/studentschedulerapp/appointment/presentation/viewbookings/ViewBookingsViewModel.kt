package com.riaanjlagrange.studentschedulerapp.appointment.presentation.viewbookings

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.riaanjlagrange.studentschedulerapp.appointment.data.repository.AppointmentRepositoryImpl
import kotlinx.coroutines.launch

class ViewBookingsViewModel : ViewModel() {

    private val repo = AppointmentRepositoryImpl()  // make sure this is properly constructed

    var state by mutableStateOf(ViewBookingsViewState())
        private set

    fun loadAppointments() {
        state = state.copy(isLoading = true)

        viewModelScope.launch {
            val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return@launch

            val result = repo.getAppointments(userId)

            result.fold(
                ifLeft = { error ->
                    state = state.copy(error = error.error.message, isLoading = false)
                },
                ifRight = { appointments ->
                    state = state.copy(appointments = appointments, isLoading = false)
                }
            )
        }
    }
}
