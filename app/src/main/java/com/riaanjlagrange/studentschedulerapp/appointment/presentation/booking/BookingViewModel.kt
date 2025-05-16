package com.riaanjlagrange.studentschedulerapp.appointment.presentation.booking

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.compose.runtime.*
import com.google.firebase.auth.FirebaseAuth
import com.riaanjlagrange.studentschedulerapp.appointment.data.repository.AppointmentRepositoryImpl
import kotlinx.coroutines.launch
import arrow.core.Either
import com.riaanjlagrange.studentschedulerapp.core.domain.model.FirestoreError

class BookingViewModel : ViewModel() {
    private val repo = AppointmentRepositoryImpl()

    var state by mutableStateOf(BookingViewState())
        private set

    fun updateDate(date: String) {
        state = state.copy(appointment = state.appointment.copy(date = date))
    }

    fun updateTime(time: String) {
        state = state.copy(appointment = state.appointment.copy(time = time))
    }
    fun book(onResult: (Boolean) -> Unit) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return

        val updatedAppointment = state.appointment.copy(userId = userId)

        state = state.copy(isLoading = true, error = null)

        viewModelScope.launch {
            val response: Either<FirestoreError, String> = repo.bookAppointment(updatedAppointment)

            response.fold(
                ifLeft = { error ->
                    state = state.copy(
                        isLoading = false,
                        error = error.error.message
                    )
                    onResult(false)
                },
                ifRight = {
                    state = state.copy(
                        isLoading = false,
                        error = null
                    )
                    onResult(true)
                }
            )
        }
    }

}
