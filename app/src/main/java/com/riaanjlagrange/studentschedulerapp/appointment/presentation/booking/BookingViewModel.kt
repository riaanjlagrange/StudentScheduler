package com.riaanjlagrange.studentschedulerapp.appointment.presentation.booking

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.compose.runtime.*
import com.google.firebase.auth.FirebaseAuth
import com.riaanjlagrange.studentschedulerapp.appointment.data.repository.AppointmentRepositoryImpl
import com.riaanjlagrange.studentschedulerapp.appointment.domain.model.FirebaseError
import kotlinx.coroutines.launch
import arrow.core.Either

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

        // Attach user ID to appointment
        val updatedAppointment = state.appointment.copy(userId = userId)

        state = state.copy(isLoading = true, error = null)

        viewModelScope.launch {
            val response: Either<FirebaseError, String> = repo.bookAppointment(updatedAppointment)

            state = when (response) {
                is Either.Right -> {
                    state.copy(isLoading = false)
                }

                is Either.Left -> state.copy(
                    isLoading = false,
                    error = response.value.error.message
                )
            }

            onResult(response.isRight())
        }
    }
}
