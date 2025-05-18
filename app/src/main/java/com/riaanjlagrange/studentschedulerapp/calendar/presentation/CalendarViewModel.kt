package com.riaanjlagrange.studentschedulerapp.calendar.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.riaanjlagrange.studentschedulerapp.appointment.data.repository.AppointmentRepositoryImpl
import com.riaanjlagrange.studentschedulerapp.core.data.repository.UsersRepositoryImp
import kotlinx.coroutines.launch

class CalendarViewModel: ViewModel() {
    private val appointmentsRepo = AppointmentRepositoryImpl()
    private val usersRepo = UsersRepositoryImp()

    var state by mutableStateOf(CalendarViewState())
        private set

    fun loadUser() {
        state = state.copy(userIsLoading = true)

        viewModelScope.launch {
            val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return@launch

            val result = usersRepo.getUserById(userId)

            result.fold(
                ifLeft = { error ->
                    state = state.copy(userError = error.error.message, userIsLoading = false)
                },
                ifRight = { user ->
                    state = state.copy(yourUser = user, userIsLoading = false)
                }
            )
        }
    }

    fun loadAppointments() {
        state = state.copy(appointmentsIsLoading = true)

        viewModelScope.launch {
            val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return@launch

            val result = appointmentsRepo.getAppointments(userId)

            result.fold(
                ifLeft = { error ->
                    state = state.copy(appointmentsError = error.error.message, appointmentsIsLoading = false)
                },
                ifRight = { appointments ->
                    state = state.copy(appointments = appointments, appointmentsIsLoading = false)
                }
            )
        }
    }
}