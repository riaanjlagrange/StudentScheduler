package com.riaanjlagrange.studentschedulerapp.appointment.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.riaanjlagrange.studentschedulerapp.appointment.data.Appointment
import com.riaanjlagrange.studentschedulerapp.appointment.data.AppointmentRepository

class BookingViewModel: ViewModel() {
    // set state variables
    var selectedDate by mutableStateOf("")
    var selectedTime by mutableStateOf("")
    var isBooking by mutableStateOf(false)

    // create instance of appointment repo
    private val repo = AppointmentRepository()

    // makes a booking
    // set onResult lambda to be used in BookingScreen
    fun book(onResult: (Boolean) -> Unit) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return
        val appointment = Appointment(
            userId = userId,
            date = selectedDate,
            time = selectedTime
        )

        // set isBooking to true
        isBooking = true

        // submit booking to model
        // set onResult lambda to of model to success
        repo.bookAppointment(appointment) { success ->
            isBooking = false
            onResult(success)
        }
    }
}