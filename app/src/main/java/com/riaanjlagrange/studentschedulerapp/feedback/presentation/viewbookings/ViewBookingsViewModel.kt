package com.riaanjlagrange.studentschedulerapp.feedback.presentation.viewbookings

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.riaanjlagrange.studentschedulerapp.appointment.data.repository.AppointmentRepositoryImpl
import com.riaanjlagrange.studentschedulerapp.core.data.repository.UsersRepositoryImp
import kotlinx.coroutines.launch

class ViewBookingsViewModel : ViewModel() {

    private val appointmentRepo = AppointmentRepositoryImpl()
    private val usersRepo = UsersRepositoryImp()

    var state by mutableStateOf(ViewBookingsViewState())
        private set

    fun loadUser() {
        state = state.copy(yourUserIsLoading = true)

        viewModelScope.launch {
            val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return@launch

            val result = usersRepo.getUserById(userId)

            result.fold(
                ifLeft = { error ->
                    state = state.copy(yourUserError = error.error.message, yourUserIsLoading = false)
                },
                ifRight = { user ->
                    state = state.copy(yourUser = user, yourUserIsLoading = false)
                }
            )
        }
    }

    fun loadAppointments() {
        state = state.copy(isLoading = true)

        viewModelScope.launch {
            val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return@launch

            val result = appointmentRepo.getAppointments(userId)

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
